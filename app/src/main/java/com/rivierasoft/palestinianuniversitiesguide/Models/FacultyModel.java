package com.rivierasoft.palestinianuniversitiesguide.Models;

import androidx.recyclerview.widget.RecyclerView;

import com.rivierasoft.palestinianuniversitiesguide.Adapters.ResultAdapter;

import java.util.ArrayList;

public class FacultyModel {
    private String faculty;
    private ArrayList<Result> results;

    public FacultyModel(String faculty, ArrayList<Result> results) {
        this.faculty = faculty;
        this.results = results;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public RecyclerView.Adapter getAdapter() {
        ResultAdapter resultAdapter = new ResultAdapter(results, 0, 0, null, null);
        return resultAdapter;
    }


}
