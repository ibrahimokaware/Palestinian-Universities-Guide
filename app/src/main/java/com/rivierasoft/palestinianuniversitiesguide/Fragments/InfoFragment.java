package com.rivierasoft.palestinianuniversitiesguide.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rivierasoft.palestinianuniversitiesguide.Adapters.InfoAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Models.Info;
import com.rivierasoft.palestinianuniversitiesguide.Models.University;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;
import com.rivierasoft.palestinianuniversitiesguide.Activities.WebActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Info> infoList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "university";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private University university;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param university Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, University university) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, university);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            university = (University) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_info);

        infoList = new ArrayList<>();
        if (mParam1.equals("a")) {
            infoList.add(new Info(R.drawable.ic_ocation_city, getString(R.string.province), university.getProvince()));
            infoList.add(new Info(R.drawable.ic_location, getString(R.string.address), university.getAddress()));
            infoList.add(new Info(R.drawable.ic_school, getString(R.string.type), university.getType()));
            infoList.add(new Info(R.drawable.ic_date, getString(R.string.year), university.getYear()));
            infoList.add(new Info(R.drawable.ic_info, getString(R.string.about), university.getAbout()));
        } else if (mParam1.equals("c")) {
            if (!university.getPhone().equals("null"))
                infoList.add(new Info(R.drawable.ic_phone, getString(R.string.phone), university.getPhone()));
            if (!university.getFax().equals("null"))
                infoList.add(new Info(R.drawable.ic_fax, getString(R.string.fax), university.getFax()));
            if (!university.getEmail().equals("null"))
                infoList.add(new Info(R.drawable.ic_email, getString(R.string.email), university.getEmail()));
            if (!university.getWebsite().equals("null"))
                infoList.add(new Info(R.drawable.ic_worldwide, getString(R.string.website), university.getWebsite()));
            if (!university.getLocation().equals("null"))
                infoList.add(new Info(R.drawable.ic_map, getString(R.string.location), "اضغط هنا"));
            if (!university.getFacebook().equals("null"))
                infoList.add(new Info(R.drawable.ic_facebook, getString(R.string.facebook), "اضغط هنا"));
        } else if (mParam1.equals("ns")) {
            if (!university.getRates().equals("null"))
                infoList.add(new Info(R.drawable.ic_how_to_reg, getString(R.string.rates), "اضغط هنا"));
            if (!university.getFees().equals("null"))
                infoList.add(new Info(R.drawable.ic_invoice, getString(R.string.fees), "اضغط هنا"));
            if (!university.getCalendar().equals("null"))
                infoList.add(new Info(R.drawable.ic_event, getString(R.string.calendar), "اضغط هنا"));
            if (!university.getScholarship().equals("null"))
                infoList.add(new Info(R.drawable.ic_beenhere, getString(R.string.scholarship), "اضغط هنا"));
        } else if (mParam1.equals("cs")) {
            if (!university.getPortal().equals("null"))
                infoList.add(new Info(R.drawable.ic_student, getString(R.string.portal), university.getPortal()));
            if (!university.getE_learning().equals("null"))
                infoList.add(new Info(R.drawable.ic_elearning, getString(R.string.e_learning), university.getE_learning()));
        }

        InfoAdapter infoAdapter = new InfoAdapter(infoList, mParam1, getContext(), new OnRVIClickListener() {
            @Override
            public void OnClickListener(int b) {
                //if (mParam1.equals("c")) {
                    if (infoList.get(b).getTitle().equals(getString(R.string.phone))) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("phone", university.getPhone());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getContext(), "تم النسخ إلى الحافظة",Toast.LENGTH_SHORT).show();
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.fax))) {
                        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("fax", university.getPhone());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getContext(), "تم النسخ إلى الحافظة",Toast.LENGTH_SHORT).show();
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.email))) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{university.getEmail()});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        intent.setPackage("com.google.android.gm");
                        intent.setType("message/rfc822");
                        //if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                            startActivity(intent);
                        //else Toast.makeText(getContext(), "التطبيق غير متوفر!", Toast.LENGTH_SHORT).show();
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.website))) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(university.getWebsite())));
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.location))) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(university.getLocation()));
                            //intent.setData(Uri.parse("https://www.google.com/maps/place/%D8%AC%D8%A7%D9%85%D8%B9%D8%A9+%D8%A7%D9%84%D9%86%D8%AC%D8%A7%D8%AD+%D8%A7%D9%84%D9%88%D8%B7%D9%86%D9%8A%D8%A9+%D8%A7%D9%84%D8%AD%D8%B1%D9%85+%D8%A7%D9%84%D8%AC%D8%AF%D9%8A%D8%AF%E2%80%AD/@32.2270557,35.2484895,15z/data=!4m5!3m4!1s0x0:0x12ebfea4ddbb7ccb!8m2!3d32.2270557!4d35.2222253"));
                            intent.setPackage("com.google.android.apps.maps");
                            startActivity(intent);
                        } catch(Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(university.getLocation())));
                        }
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.facebook))) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+ university.getFacebook())));
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+ university.getFacebook())));
                        }
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.portal))) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(university.getPortal())));
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.e_learning))) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(university.getE_learning())));
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.rates))) {
                        startActivity(new Intent(getContext(), WebActivity.class).putExtra("link", university.getRates()));
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.fees))) {
                        startActivity(new Intent(getContext(), WebActivity.class).putExtra("link", university.getFees()));
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.calendar))) {
                        startActivity(new Intent(getContext(), WebActivity.class).putExtra("link", university.getCalendar()));
                    } else if (infoList.get(b).getTitle().equals(getString(R.string.scholarship))) {
                        startActivity(new Intent(getContext(), WebActivity.class).putExtra("link", university.getScholarship()));
                    }
                //}
//                if (mParam1.equals("ns")) {
//
//                }
//                if (mParam1.equals("cs")) {
//
//                }
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(infoAdapter);
    }
}