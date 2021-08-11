package com.rivierasoft.palestinianuniversitiesguide.Models;

public class SavedProgram {
    private int id;
    private String documentID;

    public SavedProgram(int id, String documentID) {
        this.id = id;
        this.documentID = documentID;
    }

    public SavedProgram(String documentID) {
        this.documentID = documentID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}
