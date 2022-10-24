package com.example.sampleapplication;

public class StudentModel {
    String name;
    String present;
    String absent;
    String excluded;

    public StudentModel(String name, String present, String absent, String excluded) {
        this.name = name;
        this.present = present;
        this.absent = absent;
        this.excluded = excluded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getAbsent() {
        return absent;
    }

    public void setAbsent(String absent) {
        this.absent = absent;
    }

    public String getExcluded() {
        return excluded;
    }

    public void setExcluded(String excluded) {
        this.excluded = excluded;
    }
}
