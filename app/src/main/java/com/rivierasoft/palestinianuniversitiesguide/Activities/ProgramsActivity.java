package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.rivierasoft.palestinianuniversitiesguide.Adapters.ProgramAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.Fragments.FacultiesFragment;
import com.rivierasoft.palestinianuniversitiesguide.LoadingAds;
import com.rivierasoft.palestinianuniversitiesguide.Models.Faculty;
import com.rivierasoft.palestinianuniversitiesguide.Models.Program;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ProgramsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    private LinearLayout linearLayout;
    private TextView facultyTV, noProgramsYetTV;

    private ArrayList<Faculty> faculties;
    private ArrayList<Faculty> facultyArrayList = new ArrayList<>();
    private ArrayList<Program> programs;
    private ArrayList<Program> programArrayList = new ArrayList<>();

    private Intent intent;
    private int university_id;
    private String university_name;
    private int program_type;
    private int faculty_id;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference facultyRef = db.collection("faculties");
    private CollectionReference programRef = db.collection("programs");

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        Configuration newConfig = new Configuration( res.getConfiguration() );
        Locale locale = new Locale( "ar" );
        newConfig.locale = locale;
        newConfig.setLocale(locale);
        newConfig.setLayoutDirection( locale );
        res.updateConfiguration( newConfig, null );
        setContentView(R.layout.activity_programs);

        Connectivity.checkConnection(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        toolbar = findViewById(R.id.tb_programs);
        setSupportActionBar(toolbar);

        intent = getIntent();
        university_id = intent.getIntExtra("university_id", 1);
        university_name = intent.getStringExtra("university_name");
        program_type = intent.getIntExtra("program_type", 1);
        faculty_id = intent.getIntExtra("faculty_id", 1);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.pb_programs);
        frameLayout = findViewById(R.id.frameLayout);
        linearLayout = findViewById(R.id.ll_programs);
        facultyTV = findViewById(R.id.tv_faculty_name);
        noProgramsYetTV = findViewById(R.id.tv_no_programs_yet);

        toolbar.setTitle(getActivityTitle(university_name));
        toolbar.setVisibility(View.VISIBLE);

        frameLayout.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<Faculty> list, ArrayList<Program> list2) {
                faculties = list;
                programs = list2;

                SharedPreferences sharedPreferences = getSharedPreferences("ads", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(!sharedPreferences.getBoolean("p", false)) {
                    Toast.makeText(getApplicationContext(), "اضغط على البرنامج لعرض التفاصيل الخاصة به", Toast.LENGTH_SHORT).show();
                    editor.putBoolean("p", true);
                    editor.apply();
                }

                if (faculties.size() == 0 || programs.size() == 0) {
                    noProgramsYetTV.setVisibility(View.VISIBLE);
                } else {
                    frameLayout.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    noProgramsYetTV.setVisibility(View.GONE);

                    final ArrayList<Faculty> orderedFaculties = new ArrayList<>();

                    ArrayList<String> facultyArrayList = new ArrayList<>();
                    for (Faculty faculty : faculties)
                        facultyArrayList.add(faculty.getName());
                    Collections.sort(facultyArrayList);

                    if (program_type == 3)
                        Collections.reverse(facultyArrayList);

                    for (String name : facultyArrayList)
                        for (Faculty faculty : faculties)
                            if (faculty.getName().equals(name))
                                orderedFaculties.add(faculty);


                    String facultyName = "";
                    if (faculty_id == 0)
                        faculty_id = orderedFaculties.get(0).getId();

                    for (Faculty faculty : orderedFaculties)
                        if (faculty.getId() == faculty_id)
                            facultyName = faculty.getName();

                    facultyTV.setText(facultyName);

                    ArrayList<Program> arrayList = new ArrayList<>();
                    for (Program p : programs)
                        if (p.getFacultyID() == faculty_id)
                            arrayList.add(p);

                    final ArrayList<Program> orderedPrograms = new ArrayList<>();

                    ArrayList<String> programArrayList = new ArrayList<>();
                    for (Program program : arrayList)
                        programArrayList.add(program.getName());
                    Collections.sort(programArrayList);

                    for (String name : programArrayList)
                        for (Program program : arrayList)
                            if (program.getName().equals(name))
                                orderedPrograms.add(program);

                    ProgramAdapter programAdapter = new ProgramAdapter(orderedPrograms, new OnRVIClickListener() {
                        @Override
                        public void OnClickListener(final int b) {
                            if (LoadingAds.isInterstitialAdLoading2) {
                                LoadingAds.mInterstitialAd2.show(ProgramsActivity.this);
                                LoadingAds.loadInterstitialAd2(getApplicationContext());
                                LoadingAds.mInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback(){
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
                    });

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(programAdapter);

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            FacultiesFragment facultiesFragment = FacultiesFragment.newInstance(orderedFaculties, university_id, university_name,
                                    program_type, faculty_id);
                            fragmentTransaction.add(android.R.id.content, facultiesFragment);
                            //fragmentTransaction.replace(R.id.fragment_container, createFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                }
            }
        });
    }

    public String getActivityTitle (String university_name) {
        String title = "";
        switch (program_type) {
            case 1: title = "برامج الدبلوم - "+university_name;
                break;
            case 2: title = "برامج البكالوريوس - "+university_name;
                break;
            case 3: title = "برامج الدراسات العليا -  "+university_name;
                break;
        }
        return title;
    }

    public void readData(final MyCallback myCallback) {
        facultyRef.whereEqualTo("university_id", university_id)
                .whereEqualTo("program_type", program_type+"")
                //.orderBy("name", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        facultyArrayList.add(new Faculty(Integer.parseInt(document.get("id").toString()),
                                document.getString("name"), Integer.parseInt(document.get("university_id").toString()),
                                document.getString("program_type"), document.getString("no_programs")));
                    }
                    programRef.whereEqualTo("university_id", university_id)
                            .whereEqualTo("program_type", program_type+"")
                            //.whereEqualTo("faculty_id", facultyArrayList.get(faculty_id-1).getId())
                            //.orderBy("name", Query.Direction.ASCENDING)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    programArrayList.add(new Program(Integer.parseInt(document.get("id").toString()), document.getId(),
                                            document.getString("name"), Integer.parseInt(document.get("faculty_id").toString()),
                                            Integer.parseInt(document.get("university_id").toString()),
                                            document.getString("program_type"), document.getString("degree"),
                                            document.getString("department"), document.getString("duration"),
                                            document.getString("rate"), document.getString("price"),
                                            document.getString("plan"), document.getString("link"),
                                            document.getString("cover"), document.getString("logo")));
                                }
                                myCallback.onCallback(facultyArrayList, programArrayList);
                            } else {
                                Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface MyCallback {
        void onCallback(ArrayList<Faculty> list, ArrayList<Program> list2);
    }
}

