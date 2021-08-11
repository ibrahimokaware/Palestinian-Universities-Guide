package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rivierasoft.palestinianuniversitiesguide.Adapters.ResultAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.LoadingAds;
import com.rivierasoft.palestinianuniversitiesguide.Models.Info;
import com.rivierasoft.palestinianuniversitiesguide.Models.Program;
import com.rivierasoft.palestinianuniversitiesguide.Models.Result;
import com.rivierasoft.palestinianuniversitiesguide.Models.SavedProgram;
import com.rivierasoft.palestinianuniversitiesguide.MyDatabase;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private MyDatabase database;

    private TextView sectionTV, averageTV, countTV;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private Intent intent;
    private int university_id, average;
    private String literary;
    private ArrayList<String> programTypeArrayList;
    private ArrayList<Integer> universitiesIDs;
    private ArrayList<Info> infoArrayList;
    private ArrayList<SavedProgram> savedPrograms = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference programRef = db.collection("programs");

    private ArrayList<Result> results = new ArrayList<>();
    private ArrayList<Program> programArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Resources res = getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        Locale locale = new Locale("ar");
        newConfig.locale = locale;
        newConfig.setLocale(locale);
        newConfig.setLayoutDirection(locale);
        res.updateConfiguration(newConfig, null);
        setContentView(R.layout.activity_result);

        Connectivity.checkConnection(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        intent = getIntent();
        university_id = intent.getIntExtra("university_id", 1);
        average = intent.getIntExtra("average", 90);
        programTypeArrayList = intent.getStringArrayListExtra("program_type");
        universitiesIDs = intent.getIntegerArrayListExtra("ids");
        literary = intent.getStringExtra("literary");
        infoArrayList = (ArrayList<Info>) intent.getSerializableExtra("info");

        sectionTV = findViewById(R.id.tv_result_section);
        averageTV = findViewById(R.id.tv_result_average);
        countTV = findViewById(R.id.tv_result_count);
        recyclerView = findViewById(R.id.rv_result);
        progressBar = findViewById(R.id.pb_result);

        countTV.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        averageTV.setText("%"+average);
        if (literary.equals("0"))
            sectionTV.setText("علمي");
        else sectionTV.setText("أدبي");

        database = new MyDatabase(getApplicationContext());
        savedPrograms = database.getSavedPrograms();

        readData(new MyCallback() {
            @Override
            public void onCallback(final ArrayList<Program> list) {

                countTV.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);

                SharedPreferences sharedPreferences = getSharedPreferences("ads", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(!sharedPreferences.getBoolean("p2", false)) {
                    Toast.makeText(getApplicationContext(), "اضغط على البرنامج لعرض التفاصيل الخاصة به", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("p2", true);
                    editor.apply();
                }

                for (Program program : list) {
                    for (Info info : infoArrayList) {
                        if (info.getIcon() == program.getUniversityID()) {
                            results.add(new Result(program.getName(), program.getDegree(), info.getTitle(), program.getDocumentID(), false));
                        }
                    }
                }

                if (savedPrograms.size() != 0) {
                    for (Result result : results) {
                        for (SavedProgram savedProgram : savedPrograms) {
                            if (savedProgram.getDocumentID().equals(result.getDocumentID())) {
                                results.set(results.indexOf(result), new Result(result.getProgram(), result.getDegree(), result.getUniversity(), result.getDocumentID(), true));
                            }
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
                for (Program program : list) {
                    programArrayList.add(program.getName()+"%"+program.getDocumentID());
                }
                Collections.sort(programArrayList);

                for (String name : programArrayList) {
                    for (Program program : list) {
                        if ((program.getName()+"%"+program.getDocumentID()).equals(name)) {
                            orderedPrograms.add(program);
                        }
                    }
                }

                if (results.size() == 0)
                    countTV.setText("لا يوجد نتائج");
                else countTV.setText("عدد النتائج: "+ results.size());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                ResultAdapter resultAdapter = new ResultAdapter(orderedResults, 1, 0, new OnRVIClickListener() {
                    @Override
                    public void OnClickListener(final int b) {
                        //Toast.makeText(getApplicationContext(), orderedPrograms.get(b).getName(), Toast.LENGTH_SHORT).show();
//                        if (mInterstitialAd != null) {
//                            mInterstitialAd.show(MyActivity.this);
//                        } else {
//                            Log.d("TAG", "The interstitial ad wasn't ready yet.")
//                        }
                        if (LoadingAds.isInterstitialAdLoading) {
                            LoadingAds.mInterstitialAd.show(ResultActivity.this);
                            LoadingAds.loadInterstitialAd(getApplicationContext());
                            LoadingAds.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when fullscreen content is dismissed.
                                    //Log.d("TAG", "The ad was dismissed.");
                                    startActivity(new Intent(getApplicationContext(), MajorActivity.class).putExtra("program", orderedPrograms.get(b)));
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when fullscreen content failed to show.
                                    //Log.d("TAG", "The ad failed to show.");
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when fullscreen content is shown.
                                    // Make sure to set your reference to null so you don't
                                    // show it a second time.
                                    //mInterstitialAd = null;
                                    //Log.d("TAG", "The ad was shown.");
                                }
                            });
                        } else {
                            startActivity(new Intent(getApplicationContext(), MajorActivity.class).putExtra("program", orderedPrograms.get(b)));
                        }
                    }
                }, new OnRVIClickListener() {
                    @Override
                    public void OnClickListener(int b) {
                        if (orderedResults.get(b).isSave()) {
                            database.unsaveProgram(new SavedProgram(orderedPrograms.get(b).getDocumentID()));
                            Toast.makeText(getApplicationContext(), "تم إلغاء الحفظ", Toast.LENGTH_SHORT).show();
                        } else {
                            database.saveProgram(new SavedProgram(orderedPrograms.get(b).getDocumentID()));
                            Toast.makeText(getApplicationContext(), "تم الحفظ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(resultAdapter);
            }
        });
    }

    public void readData(final MyCallback myCallback) {
        if (university_id == 0) {
            if (literary.equals("0")) {
                programRef
                        .whereIn("program_type", programTypeArrayList)
                        //.whereLessThanOrEqualTo("average_s", average)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for (int id : universitiesIDs) {
                                    if (Integer.parseInt(document.get("university_id").toString()) == id) {
                                        if (Integer.parseInt(document.get("average_s").toString()) <= average) {
                                            programArrayList.add(new Program(Integer.parseInt(document.get("id").toString()), document.getId(),
                                                    document.getString("name"), Integer.parseInt(document.get("faculty_id").toString()),
                                                    Integer.parseInt(document.get("university_id").toString()),
                                                    document.getString("program_type"), document.getString("degree"),
                                                    document.getString("department"), document.getString("duration"),
                                                    document.getString("rate"), document.getString("price"),
                                                    document.getString("plan"), document.getString("link"),
                                                    document.getString("cover"), document.getString("logo")));
                                        }
                                    }
                                }
                            }
                            myCallback.onCallback(programArrayList);
                        } else {
                            Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                programRef
                        .whereIn("program_type", programTypeArrayList)
                        .whereEqualTo("literary", literary)
                        //.whereLessThanOrEqualTo("average_l", average)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                for (int id : universitiesIDs) {
                                    if (Integer.parseInt(document.get("university_id").toString()) == id) {
                                        if (Integer.parseInt(document.get("average_l").toString()) <= average) {
                                            programArrayList.add(new Program(Integer.parseInt(document.get("id").toString()), document.getId(),
                                                    document.getString("name"), Integer.parseInt(document.get("faculty_id").toString()),
                                                    Integer.parseInt(document.get("university_id").toString()),
                                                    document.getString("program_type"), document.getString("degree"),
                                                    document.getString("department"), document.getString("duration"),
                                                    document.getString("rate"), document.getString("price"),
                                                    document.getString("plan"), document.getString("link"),
                                                    document.getString("cover"), document.getString("logo")));
                                        }
                                    }
                                }
                            }
                            myCallback.onCallback(programArrayList);
                        } else {
                            Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            if (literary.equals("0")) {
                programRef
                        .whereEqualTo("university_id", university_id)
                        .whereIn("program_type", programTypeArrayList)
                        //.whereLessThanOrEqualTo("average_s", average)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (Integer.parseInt(document.get("average_s").toString()) <= average) {
                                    programArrayList.add(new Program(Integer.parseInt(document.get("id").toString()), document.getId(),
                                            document.getString("name"), Integer.parseInt(document.get("faculty_id").toString()),
                                            Integer.parseInt(document.get("university_id").toString()),
                                            document.getString("program_type"), document.getString("degree"),
                                            document.getString("department"), document.getString("duration"),
                                            document.getString("rate"), document.getString("price"),
                                            document.getString("plan"), document.getString("link"),
                                            document.getString("cover"), document.getString("logo")));
                                }
                            }
                            myCallback.onCallback(programArrayList);
                        } else {
                            Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                programRef.whereEqualTo("university_id", university_id)
                        .whereIn("program_type", programTypeArrayList)
                        .whereEqualTo("literary", literary)
                        //.whereLessThanOrEqualTo("average_l", average)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (Integer.parseInt(document.get("average_l").toString()) <= average) {
                                    programArrayList.add(new Program(Integer.parseInt(document.get("id").toString()), document.getId(),
                                            document.getString("name"), Integer.parseInt(document.get("faculty_id").toString()),
                                            Integer.parseInt(document.get("university_id").toString()),
                                            document.getString("program_type"), document.getString("degree"),
                                            document.getString("department"), document.getString("duration"),
                                            document.getString("rate"), document.getString("price"),
                                            document.getString("plan"), document.getString("link"),
                                            document.getString("cover"), document.getString("logo")));
                                }
                            }
                            myCallback.onCallback(programArrayList);
                        } else {
                            Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    public interface MyCallback {
        void onCallback(ArrayList<Program> list);
    }
}