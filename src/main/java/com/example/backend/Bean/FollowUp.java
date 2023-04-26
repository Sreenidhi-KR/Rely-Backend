package com.example.backend.Bean;

import java.util.Date;

public class FollowUp {
    private int consultationId;
    private Date followUpDate;
    private int doctorId;

    private String fname;

    private String lname;

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

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

    public FollowUp(int consultationId, Date followUpDate, int doctorId, String fname, String lname) {
        this.consultationId = consultationId;
        this.followUpDate = followUpDate;
        this.doctorId = doctorId;
        this.fname = fname;
        this.lname =lname;
    }
}
