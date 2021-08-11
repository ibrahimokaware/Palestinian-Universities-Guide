package com.rivierasoft.palestinianuniversitiesguide.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rivierasoft.palestinianuniversitiesguide.Adapters.FacultiesAdapter;
import com.rivierasoft.palestinianuniversitiesguide.Models.Faculty;
import com.rivierasoft.palestinianuniversitiesguide.OnRVIClickListener;
import com.rivierasoft.palestinianuniversitiesguide.Activities.ProgramsActivity;
import com.rivierasoft.palestinianuniversitiesguide.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FacultiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacultiesFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "facultyArrayList";
    private static final String ARG_PARAM2 = "university_id";
    private static final String ARG_PARAM3 = "university_name";
    private static final String ARG_PARAM4 = "program_type";
    private static final String ARG_PARAM5 = "faculty_id";

    // TODO: Rename and change types of parameters
    private ArrayList<Faculty> facultyArrayList;
    private int university_id;
    private String university_name;
    private int program_type;
    private int faculty_id;

    public FacultiesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FacultiesFragment newInstance(ArrayList<Faculty> facultyArrayList, int university_id, String university_name,
                                                int program_type, int faculty_id) {
        FacultiesFragment fragment = new FacultiesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, facultyArrayList);
        args.putInt(ARG_PARAM2, university_id);
        args.putString(ARG_PARAM3, university_name);
        args.putInt(ARG_PARAM4, program_type);
        args.putInt(ARG_PARAM5, faculty_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            facultyArrayList = (ArrayList<Faculty>) getArguments().getSerializable(ARG_PARAM1);
            university_id = getArguments().getInt(ARG_PARAM2);
            university_name = getArguments().getString(ARG_PARAM3);
            program_type = getArguments().getInt(ARG_PARAM4);
            faculty_id = getArguments().getInt(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faculties, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = getActivity().findViewById(R.id.tb_programs);
        recyclerView = view.findViewById(R.id.rv_faculties);
        imageView = view.findViewById(R.id.iv_finish);

        toolbar.setVisibility(View.GONE);

        FacultiesAdapter facultiesAdapter = new FacultiesAdapter(facultyArrayList, faculty_id, getContext(), new OnRVIClickListener() {
            @Override
            public void OnClickListener(int b) {
                getActivity().finish();
                startActivity(new Intent(getContext(), ProgramsActivity.class).putExtra("university_id", university_id)
                        .putExtra("university_name", university_name).putExtra("program_type", program_type)
                        .putExtra("faculty_id", facultyArrayList.get(b).getId()));
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(facultiesAdapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbar.setVisibility(View.VISIBLE);
    }
}