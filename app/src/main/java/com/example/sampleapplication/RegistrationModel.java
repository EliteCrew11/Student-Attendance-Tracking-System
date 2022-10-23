package com.example.sampleapplication;

public class RegistrationModel {
    String FirstName;
    String LastName;
    String sid;
    String pass;
    String course;
    String type;

    public RegistrationModel() {
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RegistrationModel(String firstName, String lastName, String sid, String pass, String course, String type) {
        FirstName = firstName;
        LastName = lastName;
        this.sid = sid;
        this.pass = pass;
        this.course = course;
        this.type = type;
    }
}
