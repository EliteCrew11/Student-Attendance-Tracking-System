package com.example.sampleapplication.student.models;

public class StudentProfileModel {
    String name;
    String present;
    String absent;
    String course;
    String studentKey;
    String late;

    public StudentProfileModel(String name, String present, String absent, String course, String studentKey, String late) {
        this.name = name;
        this.present = present;
        this.absent = absent;
        this.course = course;
        this.studentKey = studentKey;
        this.late = late;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public StudentProfileModel() {
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudentKey() {
        return studentKey;
    }

    public void setStudentKey(String studentKey) {
        this.studentKey = studentKey;
    }

    public StudentProfileModel(String name, String present, String absent, String course, String studentKey) {
        this.name = name;
        this.present = present;
        this.absent = absent;
        this.course = course;
        this.studentKey = studentKey;
    }
}
