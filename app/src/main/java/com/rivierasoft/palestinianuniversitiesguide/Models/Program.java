package com.rivierasoft.palestinianuniversitiesguide.Models;

import java.io.Serializable;

public class Program implements Serializable {
    private int id;
    private String documentID;
    private String name;
    private int facultyID;
    private int universityID;
    private String programType;
    private String degree;
    private String department;
    private String duration;
    private String rate;
    private String price;
    private String plan;
    private String link;
    private String cover;
    private String logo;

    public Program(int id, String documentID, String name, int facultyID, int universityID, String programType, String degree, String department, String duration, String rate, String price, String plan, String link, String cover, String logo) {
        this.id = id;
        this.documentID = documentID;
        this.name = name;
        this.facultyID = facultyID;
        this.universityID = universityID;
        this.programType = programType;
        this.degree = degree;
        this.department = department;
        this.duration = duration;
        this.rate = rate;
        this.price = price;
        this.plan = plan;
        this.link = link;
        this.cover = cover;
        this.logo = logo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(int facultyID) {
        this.facultyID = facultyID;
    }

    public int getUniversityID() {
        return universityID;
    }

    public void setUniversityID(int universityID) {
        this.universityID = universityID;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
