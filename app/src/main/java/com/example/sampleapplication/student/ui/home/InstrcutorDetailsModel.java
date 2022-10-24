package com.example.sampleapplication.student.ui.home;

public class InstrcutorDetailsModel {
    String name;
    String instructor;
    String email;
    String officeMWF;
    String officeTR;
    String office;
    String phone;
    String prequisites;
    String materials;
    String description;

    public InstrcutorDetailsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeMWF() {
        return officeMWF;
    }

    public void setOfficeMWF(String officeMWF) {
        this.officeMWF = officeMWF;
    }

    public String getOfficeTR() {
        return officeTR;
    }

    public void setOfficeTR(String officeTR) {
        this.officeTR = officeTR;
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

    public String getPrequisites() {
        return prequisites;
    }

    public void setPrequisites(String prequisites) {
        this.prequisites = prequisites;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InstrcutorDetailsModel(String description, String email, String instructor, String materials, String name, String office, String officeMWF, String officeTR, String phone, String prequisites) {
        this.name = name;
        this.instructor = instructor;
        this.email = email;
        this.officeMWF = officeMWF;
        this.officeTR = officeTR;
        this.office = office;
        this.phone = phone;
        this.prequisites = prequisites;
        this.materials = materials;
        this.description = description;
    }

}
