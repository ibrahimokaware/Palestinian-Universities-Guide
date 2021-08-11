package com.rivierasoft.palestinianuniversitiesguide.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rivierasoft.palestinianuniversitiesguide.Connectivity;
import com.rivierasoft.palestinianuniversitiesguide.LoadingAds;
import com.rivierasoft.palestinianuniversitiesguide.Models.Info;
import com.rivierasoft.palestinianuniversitiesguide.Models.University;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int adsCount;

    private static final String DIALOG_TITLE = "عرض النتائج";
    private static final String DIALOG_MESSAGE = "لعرض نتائج البحث يتوجب عليك مشاهدة إعلان.\nيُعرض هذا الإعلان مرة واحدة فقط لكل جلسة استخدام.";
    private static final String DIALOG_TITLE2 = "مشاهدة إعلان";
    private static final String DIALOG_MESSAGE2 = "ساعدنا على الاستمرار في العمل بمشاهدة إعلان";

    private TextView scientificTV, literaryTV, averageTV;
    private SeekBar averageSB;
    private RadioGroup placeRG;
    private RadioButton bankRB;
    private Spinner universitiesSP;
    private CheckBox diplomaCB, bachelorCB;
    private Button searchBT;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference uniRef = db.collection("universities");

    private ArrayList<String> programTypeArrayList = new ArrayList<>();
    private ArrayList<String> bankUniversities = new ArrayList<>();
    private ArrayList<String> gazaUniversities = new ArrayList<>();
    private ArrayList<Integer> bankUniversitiesIDs = new ArrayList<>();
    private ArrayList<Integer> gazaUniversitiesIDs = new ArrayList<>();
    private ArrayList<Integer> universitiesIDs = new ArrayList<>();
    private ArrayList<University> universities;
    private ArrayList<University> universityArrayList = new ArrayList<>();
    private ArrayList<Info> infoArrayList = new ArrayList<>();

    private String literary, place;
    private int average, university_id;
    private boolean isScientific = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        Locale locale = new Locale("ar");
        newConfig.locale = locale;
        newConfig.setLocale(locale);
        newConfig.setLayoutDirection(locale);
        res.updateConfiguration(newConfig, null);
        setContentView(R.layout.activity_search);

        Connectivity.checkConnection(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        sharedPreferences = getSharedPreferences("ads", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        adsCount = sharedPreferences.getInt("ria", 0);

        scientificTV = findViewById(R.id.tv_search_scientific);
        literaryTV = findViewById(R.id.tv_search_literary);
        averageTV = findViewById(R.id.tv_search_average_value);
        averageSB = findViewById(R.id.sb_search_average);
        placeRG = findViewById(R.id.rg_search_place);
        bankRB = findViewById(R.id.rb_search_bank);
        universitiesSP = findViewById(R.id.sp_search_universities);
        diplomaCB = findViewById(R.id.cb_search_diploma);
        bachelorCB = findViewById(R.id.cb_search_bachelor);
        searchBT = findViewById(R.id.bt_search);
        progressBar = findViewById(R.id.pb_search);
        scrollView = findViewById(R.id.sv_search);

        scrollView.setVisibility(View.GONE);

        readData(new MyCallback() {
            @Override
            public void onCallback(final ArrayList<University> list) {
                universities = list;

                for (University university : list)
                    if (university.getPlace().equals("b")) {
                        bankUniversities.add(university.getName());
                        bankUniversitiesIDs.add(university.getId());
                    } else {
                        gazaUniversities.add(university.getName());
                        gazaUniversitiesIDs.add(university.getId());
                    }

                for (University university : universities)
                    infoArrayList.add(new Info(university.getId(), university.getName(), null));

                bankUniversities.add("كل الجامعات");
                gazaUniversities.add("كل الجامعات");

                scrollView.setVisibility(View.VISIBLE);

                universitiesSP.setEnabled(false);
                diplomaCB.setEnabled(false);
                bachelorCB.setEnabled(false);

                scientificTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isScientific) {
                            scientificTV.setBackgroundResource(R.drawable.branch_right_bg);
                            scientificTV.setTextColor(getResources().getColor(R.color.colorWhite));
                            literaryTV.setBackgroundResource(R.drawable.branch_left_border);
                            literaryTV.setTextColor(getResources().getColor(R.color.colorPrimary));
                            isScientific = true;
                        }
                    }
                });

                literaryTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isScientific) {
                            literaryTV.setBackgroundResource(R.drawable.branch_left_bg);
                            literaryTV.setTextColor(getResources().getColor(R.color.colorWhite));
                            scientificTV.setBackgroundResource(R.drawable.branch_right_border);
                            scientificTV.setTextColor(getResources().getColor(R.color.colorPrimary));
                            isScientific = false;
                        }
                    }
                });

                averageSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        averageTV.setText("%"+progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                placeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        checkValidation();

                        universitiesSP.setEnabled(true);
                        //int checkedId = answer_rg.getCheckedRadioButtonId();
                        //answer = findViewById(checkedId);
                        //a=answer.getText().toString();
                        //RadioButton radioButton = findViewById(checkedId);
                        //Toast.makeText(getApplicationContext(), radioButton.getText().toString(), Toast.LENGTH_SHORT).show();

                        if (bankRB.isChecked()) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    getApplicationContext(), android.R.layout.simple_spinner_item, bankUniversities);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            universitiesSP.setAdapter(adapter);
                            place = "b";
                        } else {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    getApplicationContext(), android.R.layout.simple_spinner_item, gazaUniversities);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            universitiesSP.setAdapter(adapter);
                            place = "g";
                        }

                        University university = null;
                        for (University u : universities)
                            if (u.getName().equals(universitiesSP.getSelectedItem().toString()))
                                university = u;

                            university_id = university.getId();

                        if (university.getDiploma().equals("1"))
                            diplomaCB.setEnabled(true);
                        if (university.getBachelor().equals("1"))
                            bachelorCB.setEnabled(true);
                    }
                });

                universitiesSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        checkValidation();

                        if (universitiesSP.getSelectedItem().toString().equals("كل الجامعات")) {
                            diplomaCB.setEnabled(true);
                            bachelorCB.setEnabled(true);
                            university_id = 0;
                        } else {
                            University university = null;
                            for (University u : universities)
                                if (u.getName().equals(universitiesSP.getSelectedItem().toString()))
                                    university = u;

                            university_id = university.getId();

                            if (university.getDiploma().equals("1"))
                                diplomaCB.setEnabled(true);
                            else diplomaCB.setEnabled(false);
                            if (university.getBachelor().equals("1"))
                                bachelorCB.setEnabled(true);
                            else bachelorCB.setEnabled(false);
                        }
                        diplomaCB.setChecked(false);
                        bachelorCB.setChecked(false);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                diplomaCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checkValidation();
                    }
                });

                bachelorCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checkValidation();
                    }
                });

                searchBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isScientific)
                            literary = "0";
                        else literary = "1";
                        average = averageSB.getProgress();
                        if (diplomaCB.isChecked())
                            programTypeArrayList.add("1");
                        if (bachelorCB.isChecked())
                            programTypeArrayList.add("2");
                        if (place.equals("b")) {
                            universitiesIDs = bankUniversitiesIDs;
                        } else {
                            universitiesIDs = gazaUniversitiesIDs;
                        }

                        if (LoadingAds.isRewardedInterstitialAdLoading) {
                            if (adsCount < 1) {
                                setAdsDialog(DIALOG_TITLE, DIALOG_MESSAGE);
                            } else {
                                setAdsDialog2(DIALOG_TITLE2, DIALOG_MESSAGE2);
                            }
                        } else {
                            startActivity(new Intent(getApplicationContext(), ResultActivity.class).putExtra("university_id", university_id)
                                    .putExtra("program_type", programTypeArrayList).putExtra("literary", literary)
                                    .putExtra("average", average).putExtra("info", infoArrayList)
                                    .putExtra("ids", universitiesIDs));
                        }
                    }
                });
            }
        });
    }

    void checkValidation() {
        if (diplomaCB.isChecked() || bachelorCB.isChecked()) {
            searchBT.setEnabled(true);
            searchBT.setTextColor(getResources().getColor(R.color.colorWhite));
        } else {
            searchBT.setEnabled(false);
            searchBT.setTextColor(getResources().getColor(R.color.colorDisable));
        }
    }

    public void setAdsDialog(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);

        builder.setMessage(message)
                .setTitle(title)
                .setIcon(R.drawable.ic_ads);

        builder.setPositiveButton("مشاهدة إعلان", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                LoadingAds.rewardedInterstitialAd.show(SearchActivity.this, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        editor.putInt("ria", adsCount++);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), ResultActivity.class).putExtra("university_id", university_id)
                                .putExtra("program_type", programTypeArrayList).putExtra("literary", literary)
                                .putExtra("average", average).putExtra("info", infoArrayList)
                                .putExtra("ids", universitiesIDs));
                        LoadingAds.rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            /** Called when the ad failed to show full screen content. */
                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                //Log.i(TAG, "onAdFailedToShowFullScreenContent");
                            }

                            /** Called when ad showed the full screen content. */
                            @Override
                            public void onAdShowedFullScreenContent() {
                                //Log.i(TAG, "onAdShowedFullScreenContent");
                            }

                            /** Called when full screen content is dismissed. */
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                //Log.i(TAG, "onAdDismissedFullScreenContent");
                            }
                        });
                    }
                });
                LoadingAds.loadRewardedInterstitialAd(getApplicationContext());
            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void setAdsDialog2(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);

        builder.setMessage(message)
                .setTitle(title)
                .setIcon(R.drawable.ic_ads);

        builder.setPositiveButton("مشاهدة إعلان", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                LoadingAds.rewardedInterstitialAd.show(SearchActivity.this, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        editor.putInt("ria", adsCount++);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), ResultActivity.class).putExtra("university_id", university_id)
                                .putExtra("program_type", programTypeArrayList).putExtra("literary", literary)
                                .putExtra("average", average).putExtra("info", infoArrayList)
                                .putExtra("ids", universitiesIDs));
                        LoadingAds.rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            /** Called when the ad failed to show full screen content. */
                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                //Log.i(TAG, "onAdFailedToShowFullScreenContent");
                            }

                            /** Called when ad showed the full screen content. */
                            @Override
                            public void onAdShowedFullScreenContent() {
                                //Log.i(TAG, "onAdShowedFullScreenContent");
                            }

                            /** Called when full screen content is dismissed. */
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                //Log.i(TAG, "onAdDismissedFullScreenContent");
                            }
                        });
                    }
                });
                LoadingAds.loadRewardedInterstitialAd(getApplicationContext());
            }
        });

        builder.setNegativeButton("تخطي", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getApplicationContext(), ResultActivity.class).putExtra("university_id", university_id)
                        .putExtra("program_type", programTypeArrayList).putExtra("literary", literary)
                        .putExtra("average", average).putExtra("info", infoArrayList)
                        .putExtra("ids", universitiesIDs));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }


    public void readData(final MyCallback myCallback) {
        uniRef.orderBy("id", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
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
                    myCallback.onCallback(universityArrayList);
                } else {
                    Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface MyCallback {
        void onCallback(ArrayList<University> list);
    }
}