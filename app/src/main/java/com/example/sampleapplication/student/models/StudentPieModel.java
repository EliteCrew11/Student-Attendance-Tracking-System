package com.example.sampleapplication.student.models;

public class StudentPieModel {
    String presentDays;
    String absentDays;
    String lateDays;

    public StudentPieModel() {
    }

    public StudentPieModel(String presentDays, String absentDays, String lateDays) {
        this.presentDays = presentDays;
        this.absentDays = absentDays;
        this.lateDays = lateDays;
    }

    public String getPresentDays() {
        return presentDays;
    }

    public void setPresentDays(String presentDays) {
        this.presentDays = presentDays;
    }

    public String getAbsentDays() {
        return absentDays;
    }

    public void setAbsentDays(String absentDays) {
        this.absentDays = absentDays;
    }

    public String getLateDays() {
        return lateDays;
    }

    public void setLateDays(String lateDays) {
        this.lateDays = lateDays;
    }
}

