package com.example.sampleapplication.student;

public class FacultyCountModel {
    String studentID;
    String noOfPresent;
    String noOfAbsent;
    String noofLate;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FacultyCountModel( String noOfPresent, String noOfAbsent, String noofLate, String date) {
        this.noOfPresent = noOfPresent;
        this.noOfAbsent = noOfAbsent;
        this.noofLate = noofLate;
        this.date = date;
    }

    public FacultyCountModel() {
    }


    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getNoOfPresent() {
        return noOfPresent;
    }

    public void setNoOfPresent(String noOfPresent) {
        this.noOfPresent = noOfPresent;
    }

    public String getNoOfAbsent() {
        return noOfAbsent;
    }

    public void setNoOfAbsent(String noOfAbsent) {
        this.noOfAbsent = noOfAbsent;
    }

    public String getNoofLate() {
        return noofLate;
    }

    public void setNoofLate(String noofLate) {
        this.noofLate = noofLate;
    }
}
