package com.example.backend.Bean;

import java.util.Date;

public class FollowUp {
    private Integer consultationId;
    private Date followUpDate;
    private Integer doctorId;

    private String fname;

    private String lname;

    public Integer getConsultationId() {
        return consultationId;
    }

    public void setConsultationId (Integer consultationId) {
        this.consultationId = consultationId;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId (Integer doctorId) {
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

    public FollowUp (Integer consultationId, Date followUpDate, Integer doctorId, String fname, String lname) {
        this.consultationId = consultationId;
        this.followUpDate = followUpDate;
        this.doctorId = doctorId;
        this.fname = fname;
        this.lname =lname;
    }
}
