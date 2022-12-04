package com.example.sampleapplication.professor;

public class StudentKeyModel {
 String studentkey;
 String studentName;

    public String getStudentkey() {
        return studentkey;
    }

    public void setStudentkey(String studentkey) {
        this.studentkey = studentkey;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public StudentKeyModel() {
    }

    public StudentKeyModel(String studentkey, String studentName) {
        this.studentkey = studentkey;
        this.studentName = studentName;
    }
}
