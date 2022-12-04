package com.example.sampleapplication.student.models;

public class SubjectAttendence {
    String presentCount;
    String absentCount;
    String lateCount;
    String totalCount;

    public String getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(String presentCount) {
        this.presentCount = presentCount;
    }

    public String getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(String absentCount) {
        this.absentCount = absentCount;
    }

    public String getLateCount() {
        return lateCount;
    }

    public void setLateCount(String lateCount) {
        this.lateCount = lateCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public SubjectAttendence() {
    }

    public SubjectAttendence(String presentCount, String absentCount, String lateCount, String totalCount) {
        this.presentCount = presentCount;
        this.absentCount = absentCount;
        this.lateCount = lateCount;
        this.totalCount = totalCount;
    }
}
