package com.rivierasoft.palestinianuniversitiesguide.Models;

import java.io.Serializable;

public class University implements Serializable {
    private int id;
    private String logo;
    private String name;
    private String place;
    private String cover;
    private String province;
    private String address;
    private String type;
    private String year;
    private String about;
    private String diploma;
    private String bachelor;
    private String graduate;
    private String rates;
    private String fees;
    private String calendar;
    private String scholarship;
    private String portal;
    private String e_learning;
    private String phone;
    private String fax;
    private String email;
    private String website;
    private String location;
    private String facebook;

    public University(int id, String logo, String name, String place, String cover, String province, String address, String type, String year, String about, String diploma, String bachelor, String graduate, String rates, String fees, String calendar, String scholarship, String portal, String e_learning, String phone, String fax, String email, String website, String location, String facebook) {
        this.id = id;
        this.logo = logo;
        this.name = name;
        this.place = place;
        this.cover = cover;
        this.province = province;
        this.address = address;
        this.type = type;
        this.year = year;
        this.about = about;
        this.diploma = diploma;
        this.bachelor = bachelor;
        this.graduate = graduate;
        this.rates = rates;
        this.fees = fees;
        this.calendar = calendar;
        this.scholarship = scholarship;
        this.portal = portal;
        this.e_learning = e_learning;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.website = website;
        this.location = location;
        this.facebook = facebook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public String getBachelor() {
        return bachelor;
    }

    public void setBachelor(String bachelor) {
        this.bachelor = bachelor;
    }

    public String getGraduate() {
        return graduate;
    }

    public void setGraduate(String graduate) {
        this.graduate = graduate;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getScholarship() {
        return scholarship;
    }

    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getE_learning() {
        return e_learning;
    }

    public void setE_learning(String e_learning) {
        this.e_learning = e_learning;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
