package com.example.sampleapplication.student.models;

public class StudentCourseAttendence {
    String present;
    String absent;
    String late;

    public StudentCourseAttendence() {
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

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public StudentCourseAttendence(String present, String absent, String late) {
        this.present = present;
        this.absent = absent;
        this.late = late;
    }
}
