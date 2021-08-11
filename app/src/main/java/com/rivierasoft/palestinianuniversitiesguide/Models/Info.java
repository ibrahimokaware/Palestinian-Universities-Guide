package com.rivierasoft.palestinianuniversitiesguide.Models;

import java.io.Serializable;

public class Info implements Serializable {
    private int icon;
    private String title;
    private String value;

    public Info(int icon, String title, String value) {
        this.icon = icon;
        this.title = title;
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
