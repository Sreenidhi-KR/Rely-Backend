package com.example.backend.Bean;

import java.sql.Timestamp;

public class PrevConsultations {

    private Timestamp startTime;
    private Timestamp endTime;
    private String doctorName;
    private int consultId;
    private String specialization;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getConsultId() {
        return consultId;
    }

    public void setConsultId(int consultId) {
        this.consultId = consultId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public PrevConsultations(Timestamp startTime, Timestamp endTime, String doctorName, int consultId, String specialization) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctorName = doctorName;
        this.consultId = consultId;
        this.specialization = specialization;
    }
}
