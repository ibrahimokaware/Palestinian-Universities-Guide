package com.rivierasoft.palestinianuniversitiesguide.Models;

public class Result {
    private String program;
    private String degree;
    private String university;
    private String documentID;
    private boolean isSave;

    public Result(String program, String degree, String university, String documentID, boolean isSave) {
        this.program = program;
        this.degree = degree;
        this.university = university;
        this.documentID = documentID;
        this.isSave = isSave;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }
}
