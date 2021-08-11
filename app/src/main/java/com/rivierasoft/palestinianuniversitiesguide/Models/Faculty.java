package com.rivierasoft.palestinianuniversitiesguide.Models;

import java.io.Serializable;

public class Faculty implements Serializable {
    private int id;
    private String name;
    private int university_id;
    private String program_type;
    private String no_programs;

    public Faculty(int id, String name, int university_id, String program_type, String no_programs) {
        this.id = id;
        this.name = name;
        this.university_id = university_id;
        this.program_type = program_type;
        this.no_programs = no_programs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(int university_id) {
        this.university_id = university_id;
    }

    public String getProgram_type() {
        return program_type;
    }

    public void setProgram_type(String program_type) {
        this.program_type = program_type;
    }

    public String getNo_programs() {
        return no_programs;
    }

    public void setNo_programs(String no_programs) {
        this.no_programs = no_programs;
    }
}
