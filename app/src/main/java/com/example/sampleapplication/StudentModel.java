package com.example.sampleapplication;

public class StudentModel {
    String name;
    String present;
    String absent;
    String excluded;
    String courseName;
    String date;
    String facultyName;

    public StudentModel() {
    }

    public StudentModel(String name, String present, String absent, String excluded, String courseName, String date, String facultyName) {
        this.name = name;
        this.present = present;
        this.absent = absent;
        this.excluded = excluded;
        this.courseName = courseName;
        this.date = date;
        this.facultyName = facultyName;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
