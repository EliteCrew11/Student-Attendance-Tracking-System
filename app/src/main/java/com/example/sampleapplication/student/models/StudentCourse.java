package com.example.sampleapplication.student.models;

import java.util.ArrayList;

public class StudentCourse {
    ArrayList<SubjectAttendence> subjectAttendenceArrayList;

    public ArrayList<SubjectAttendence> getSubjectAttendenceArrayList() {
        return subjectAttendenceArrayList;
    }

    public void setSubjectAttendenceArrayList(ArrayList<SubjectAttendence> subjectAttendenceArrayList) {
        this.subjectAttendenceArrayList = subjectAttendenceArrayList;
    }

    public StudentCourse() {
    }

    public StudentCourse(ArrayList<SubjectAttendence> subjectAttendenceArrayList) {
        this.subjectAttendenceArrayList = subjectAttendenceArrayList;
    }
}
