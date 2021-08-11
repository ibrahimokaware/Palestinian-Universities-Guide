package com.rivierasoft.palestinianuniversitiesguide.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.rivierasoft.palestinianuniversitiesguide.Activities.ResultActivity;
import com.rivierasoft.palestinianuniversitiesguide.Activities.SearchActivity;
import com.rivierasoft.palestinianuniversitiesguide.LoadingAds;
import com.rivierasoft.palestinianuniversitiesguide.Models.University;
import com.rivierasoft.palestinianuniversitiesguide.Activities.ProgramsActivity;
import com.rivierasoft.palestinianuniversitiesguide.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgramsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int adsCount;

    private static final String DIALOG_TITLE = "عرض البرامج";
    private static final String DIALOG_MESSAGE = "لعرض البرامج يتوجب عليك مشاهدة إعلان.\nيُعرض هذا الإعلان مرة واحدة فقط لكل جلسة استخدام.";
    private static final String DIALOG_TITLE2 = "مشاهدة إعلان";
    private static final String DIALOG_MESSAGE2 = "ساعدنا على الاستمرار في العمل بمشاهدة إعلان";

    private Button diplomaBT, bachelorBT, graduateBT;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private University university;
    private String mParam2;

    public ProgramsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param university Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgramsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgramsFragment newInstance(University university, String param2) {
        ProgramsFragment fragment = new ProgramsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, university);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            university = (University) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_programs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        sharedPreferences = getActivity().getSharedPreferences("ads", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        adsCount = sharedPreferences.getInt("ria2", 0);

        diplomaBT = view.findViewById(R.id.bt_diploma);
        bachelorBT = view.findViewById(R.id.bt_bachelor);
        graduateBT = view.findViewById(R.id.bt_graduate);

        if (university.getDiploma().equals("1"))
            diplomaBT.setVisibility(View.VISIBLE);

        if (university.getBachelor().equals("0"))
            bachelorBT.setVisibility(View.GONE);

        if (university.getGraduate().equals("0"))
            graduateBT.setVisibility(View.GONE);

        diplomaBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoadingAds.isRewardedInterstitialAdLoading2) {
                    if (adsCount < 1) {
                        setAdsDialog(DIALOG_TITLE, DIALOG_MESSAGE, 1);
                    } else {
                        setAdsDialog2(DIALOG_TITLE2, DIALOG_MESSAGE2, 1);
                    }
                } else {
                    startActivity(new Intent(getContext(), ProgramsActivity.class).putExtra("university_id", university.getId())
                            .putExtra("university_name", university.getName()).putExtra("program_type", 1)
                            .putExtra("faculty_id", 0));
                }
            }
        });

        bachelorBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoadingAds.isRewardedInterstitialAdLoading2) {
                    if (adsCount < 1) {
                        setAdsDialog(DIALOG_TITLE, DIALOG_MESSAGE, 2);
                    } else {
                        setAdsDialog2(DIALOG_TITLE2, DIALOG_MESSAGE2, 2);
                    }
                } else {
                    startActivity(new Intent(getContext(), ProgramsActivity.class).putExtra("university_id", university.getId())
                            .putExtra("university_name", university.getName()).putExtra("program_type", 2)
                            .putExtra("faculty_id", 0));
                }
            }
        });

        graduateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoadingAds.isRewardedInterstitialAdLoading2) {
                    if (adsCount < 1) {
                        setAdsDialog(DIALOG_TITLE, DIALOG_MESSAGE, 3);
                    } else {
                        setAdsDialog2(DIALOG_TITLE2, DIALOG_MESSAGE2, 3);
                    }
                } else {
                    startActivity(new Intent(getContext(), ProgramsActivity.class).putExtra("university_id", university.getId())
                            .putExtra("university_name", university.getName()).putExtra("program_type", 3)
                            .putExtra("faculty_id", 0));
                }
            }
        });
    }

    public void setAdsDialog(String title, String message, final int programType) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message)
                .setTitle(title)
                .setIcon(R.drawable.ic_ads);

        builder.setPositiveButton("مشاهدة إعلان", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                LoadingAds.rewardedInterstitialAd2.show(getActivity(), new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        editor.putInt("ria2", adsCount++);
                        editor.apply();
                        startActivity(new Intent(getContext(), ProgramsActivity.class).putExtra("university_id", university.getId())
                                .putExtra("university_name", university.getName()).putExtra("program_type", programType)
                                .putExtra("faculty_id", 0));
                        LoadingAds.rewardedInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
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
                LoadingAds.loadRewardedInterstitialAd2(getActivity());
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

    public void setAdsDialog2(String title, String message, final int programType) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(message)
                .setTitle(title)
                .setIcon(R.drawable.ic_ads);

        builder.setPositiveButton("مشاهدة إعلان", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                LoadingAds.rewardedInterstitialAd2.show(getActivity(), new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        editor.putInt("ria2", adsCount++);
                        editor.apply();
                        startActivity(new Intent(getContext(), ProgramsActivity.class).putExtra("university_id", university.getId())
                                .putExtra("university_name", university.getName()).putExtra("program_type", programType)
                                .putExtra("faculty_id", 0));
                        LoadingAds.rewardedInterstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
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
                LoadingAds.loadRewardedInterstitialAd2(getActivity());
            }
        });

        builder.setNegativeButton("تخطي", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getContext(), ProgramsActivity.class).putExtra("university_id", university.getId())
                        .putExtra("university_name", university.getName()).putExtra("program_type", programType)
                        .putExtra("faculty_id", 0));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

}