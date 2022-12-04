package com.example.sampleapplication.student.models;

import java.util.ArrayList;

public class StudentCourseList {
    ArrayList<StudentCourseAttendence> studentCourseAttendences;

    public ArrayList<StudentCourseAttendence> getStudentCourseAttendences() {
        return studentCourseAttendences;
    }

    public void setStudentCourseAttendences(ArrayList<StudentCourseAttendence> studentCourseAttendences) {
        this.studentCourseAttendences = studentCourseAttendences;
    }

    public StudentCourseList() {
    }

    public StudentCourseList(ArrayList<StudentCourseAttendence> studentCourseAttendences) {
        this.studentCourseAttendences = studentCourseAttendences;
    }
}
