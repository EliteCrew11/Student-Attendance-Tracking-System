package com.example.sampleapplication.student.models;

import java.util.ArrayList;

public class StudentRegistrationModel {
    String fname;
    String lname;
    String uniqueKey;
    String studentMailID;
    String password;
    String courseList;
    String type;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getStudentMailID() {
        return studentMailID;
    }

    public void setStudentMailID(String studentMailID) {
        this.studentMailID = studentMailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCourseList() {
        return courseList;
    }

    public void setCourseList(String courseList) {
        this.courseList = courseList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StudentRegistrationModel(String fname, String lname, String uniqueKey, String studentMailID, String password, String courseList, String type) {
        this.fname = fname;
        this.lname = lname;
        this.uniqueKey = uniqueKey;
        this.studentMailID = studentMailID;
        this.password = password;
        this.courseList = courseList;
        this.type = type;
    }

    public StudentRegistrationModel() {
    }
}



