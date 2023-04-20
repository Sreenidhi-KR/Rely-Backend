package com.example.backend.Bean;

import java.util.Date;

public class FollowUp {
    private int consultationId;
    private Date followUpDate;
    private int doctorId;

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

    public FollowUp(int consultationId, Date followUpDate, int doctorId) {
        this.consultationId = consultationId;
        this.followUpDate = followUpDate;
        this.doctorId = doctorId;
    }
}
