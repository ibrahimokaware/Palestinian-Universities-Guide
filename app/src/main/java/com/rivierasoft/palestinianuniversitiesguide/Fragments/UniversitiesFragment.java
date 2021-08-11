package com.rivierasoft.palestinianuniversitiesguide.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.rivierasoft.palestinianuniversitiesguide.Adapters.UniversityAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Models.University;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.R;
import com.rivierasoft.palestinianuniversitiesguide.Activities.UniversityActivity;

import java.util.ArrayList;


public class UniversitiesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference uniRef = db.collection("universities");

    private ArrayList<University> universities;
    private ArrayList<University> universityArrayList = new ArrayList<>();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    public UniversitiesFragment() {
        // Required empty public constructor
    }


    public static UniversitiesFragment newInstance(int param1, String param2) {
        UniversitiesFragment fragment = new UniversitiesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_universities, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_universities);
        progressBar = getActivity().findViewById(R.id.pb_main);
        toolbar = getActivity().findViewById(R.id.toolbar);
        tabLayout = getActivity().findViewById(R.id.tabLayout);
        viewPager = getActivity().findViewById(R.id.viewPager);

        toolbar.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

        universities = new ArrayList<>();

        readData(new MyCallback() {
            @Override
            public void onCallback(ArrayList<University> list) {
                //universities = list;

                toolbar.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);

                for (University university : list)
                    if (university.getPlace().equals(mParam2))
                        universities.add(university);

                UniversityAdapter adapter = new UniversityAdapter(universities, getContext(), new OnRVIClickListener() {
                    @Override
                    public void OnClickListener(int b) {
                        startActivity(new Intent(getActivity(), UniversityActivity.class)
                                .putExtra("data", universities.get(b)));
                    }
                });

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
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
                    Toast.makeText(getContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public interface MyCallback {
        void onCallback(ArrayList<University> list);
    }
}
