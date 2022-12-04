package com.example.sampleapplication.professor.ui.profile;

public class InstructorProfileModel {
    String courseName;
    String name;
    String type;
    String email;
    String office;
    String phone;
    int photo;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public InstructorProfileModel(String courseName, String name, String type, String email, String office, String phone, int photo) {
        this.courseName = courseName;
        this.name = name;
        this.type = type;
        this.email = email;
        this.office = office;
        this.phone = phone;
        this.photo = photo;
    }
}
