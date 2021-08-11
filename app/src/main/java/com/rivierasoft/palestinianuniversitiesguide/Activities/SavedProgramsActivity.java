package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rivierasoft.palestinianuniversitiesguide.Adapters.ResultAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.LoadingAds;
import com.rivierasoft.palestinianuniversitiesguide.Models.Info;
import com.rivierasoft.palestinianuniversitiesguide.Models.Program;
import com.rivierasoft.palestinianuniversitiesguide.Models.Result;
import com.rivierasoft.palestinianuniversitiesguide.Models.SavedProgram;
import com.rivierasoft.palestinianuniversitiesguide.Models.University;
import com.rivierasoft.palestinianuniversitiesguide.MyDatabase;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class SavedProgramsActivity extends AppCompatActivity {

    private MyDatabase database;

    private TextView countTV;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference uniRef = db.collection("universities");
    private CollectionReference programRef = db.collection("programs");

    private ArrayList<University> universityArrayList = new ArrayList<>();
    private ArrayList<Program> programArrayList = new ArrayList<>();
    private ArrayList<SavedProgram> savedPrograms = new ArrayList<>();
    private ArrayList<Result> results = new ArrayList<>();
    private ArrayList<Info> infoArrayList = new ArrayList<>();

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        final Configuration newConfig = new Configuration(res.getConfiguration());
        Locale locale = new Locale("ar");
        newConfig.locale = locale;
        newConfig.setLocale(locale);
        newConfig.setLayoutDirection(locale);
        res.updateConfiguration(newConfig, null);
        setContentView(R.layout.activity_saved_programs);

        Connectivity.checkConnection(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        countTV = findViewById(R.id.tv_result_count);
        recyclerView = findViewById(R.id.rv_result);
        progressBar = findViewById(R.id.pb_result);

        database = new MyDatabase(getApplicationContext());
        savedPrograms = database.getSavedPrograms();
        if (savedPrograms.size() == 0) {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            countTV.setText("لا يوجد برامج محفوظة");
        } else {
            countTV.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);

            readData(new MyCallback() {
                @Override
                public void onCallback(final ArrayList<University> list, final ArrayList<Program> list2) {

                    if (list2.size() == savedPrograms.size()) {
                        countTV.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        countTV.setText("عدد البرامج المحفوظة: "+ savedPrograms.size());

                        count = savedPrograms.size();

                        for (University university : list) {
                            infoArrayList.add(new Info(university.getId(), university.getName(), null));
                        }

                        for (Program program : list2) {
                            for (Info info : infoArrayList) {
                                if (info.getIcon() == program.getUniversityID()) {
                                    results.add(new Result(program.getName(), program.getDegree(), info.getTitle(), program.getDocumentID(), true));
                                }
                            }
                        }

                        final ArrayList<Result> orderedResults = new ArrayList<>();

                        ArrayList<String> resultArrayList = new ArrayList<>();
                        for (Result result : results) {
                            resultArrayList.add(result.getProgram()+"%"+result.getDocumentID());
                        }
                        Collections.sort(resultArrayList);

                        for (String name : resultArrayList) {
                            for (Result result : results) {
                                if ((result.getProgram()+"%"+result.getDocumentID()).equals(name)) {
                                    orderedResults.add(result);
                                }
                            }
                        }

                        final ArrayList<Program> orderedPrograms = new ArrayList<>();

                        ArrayList<String> programArrayList = new ArrayList<>();
                        for (Program program : list2) {
                            programArrayList.add(program.getName()+"%"+program.getDocumentID());
                        }
                        Collections.sort(programArrayList);

                        for (String name : programArrayList) {
                            for (Program program : list2) {
                                if ((program.getName()+"%"+program.getDocumentID()).equals(name)) {
                                    orderedPrograms.add(program);
                                }
                            }
                        }


                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                        ResultAdapter resultAdapter = new ResultAdapter(orderedResults, 2, 0, new OnRVIClickListener() {
                            @Override
                            public void OnClickListener(final int b) {
                                //Toast.makeText(getApplicationContext(), orderedPrograms.get(b).getName(), Toast.LENGTH_SHORT).show();
//                                if (LoadingAds.isInterstitialAdLoading) {
//                                    LoadingAds.mInterstitialAd.show(SavedProgramsActivity.this);
//                                    LoadingAds.loadInterstitialAd(getApplicationContext());
//                                    LoadingAds.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
//                                        @Override
//                                        public void onAdDismissedFullScreenContent() {
                                            // Called when fullscreen content is dismissed.
                                            //Log.d("TAG", "The ad was dismissed.");
//                                            startActivity(new Intent(getApplicationContext(), MajorActivity.class).putExtra("program", orderedPrograms.get(b)));
//                                        }
//
//                                        @Override
//                                        public void onAdFailedToShowFullScreenContent(AdError adError) {
//                                            // Called when fullscreen content failed to show.
//                                            //Log.d("TAG", "The ad failed to show.");
//                                        }
//
//                                        @Override
//                                        public void onAdShowedFullScreenContent() {
//                                            // Called when fullscreen content is shown.
//                                            // Make sure to set your reference to null so you don't
//                                            // show it a second time.
//                                            //mInterstitialAd = null;
//                                            //Log.d("TAG", "The ad was shown.");
//                                        }
//                                    });
//                                } else {
                                    startActivity(new Intent(getApplicationContext(), MajorActivity.class).putExtra("program", orderedPrograms.get(b)));
                                //}
                            }
                        }, new OnRVIClickListener() {
                            @Override
                            public void OnClickListener(int b) {
                                database.unsaveProgram(new SavedProgram(orderedPrograms.get(b).getDocumentID()));
                                count--;
                                if (count != 0)
                                    countTV.setText("عدد البرامج المحفوظة: "+ count);
                                else countTV.setText("لا يوجد برامج محفوظة");
                            }
                        });

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(resultAdapter);
                    }
                }
            });
        }
    }

    public void readData(final MyCallback myCallback) {

        uniRef.orderBy("id", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        universityArrayList.add(new University(Integer.parseInt(document.get("id").toString()),
                                document.getString("logo"), document.getString("name"), document.getString("place"),
                                document.getString("cover"), document.getString("province"), document.getString("address"),
                                document.getString("type"), document.getString("year"), document.getString("about"),
                                document.getString("diploma"), document.getString("bachelor"), document.getString("graduate"),
                                document.getString("rates"), document.getString("fees"),
                                document.getString("calendar"), document.getString("scholarship"),
                                document.getString("portal"), document.getString("e_learning"), document.getString("phone"),
                                document.getString("fax"), document.getString("email"), document.getString("website"),
                                document.getString("location"), document.getString("facebook")));
                    }
                    for (SavedProgram savedProgram : savedPrograms) {
                        //DocumentReference docRef = db.collection("programs").document("027ifZtwDz3hkGG8KfUn");
                        programRef.document(savedProgram.getDocumentID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        programArrayList.add(new Program(Integer.parseInt(document.get("id").toString()), document.getId(),
                                                document.getString("name"), Integer.parseInt(document.get("faculty_id").toString()),
                                                Integer.parseInt(document.get("university_id").toString()),
                                                document.getString("program_type"), document.getString("degree"),
                                                document.getString("department"), document.getString("duration"),
                                                document.getString("rate"), document.getString("price"),
                                                document.getString("plan"), document.getString("link"),
                                                document.getString("cover"), document.getString("logo")));
                                        myCallback.onCallback(universityArrayList, programArrayList);
                                        //Toast.makeText(getApplicationContext(), document.getString("name"), Toast.LENGTH_SHORT).show();
                                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    } else {
                                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                                        //Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
                                    //Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface MyCallback {
        void onCallback(ArrayList<University> list, ArrayList<Program> list2);
    }
}