package com.example.backend.Bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class PrevConsultations {

    private Timestamp startTime;
    private Timestamp endTime;
    private String doctorName;
    private int doctorId;
    private int consultId;
    private String specialization;
    private String patientName;
    private Integer prescription;

    private Date followUpDate;

    private List<DocumentDetails> documentDetailsList;

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public Integer getPrescription() {
        return prescription;
    }

    public void setPrescription(Integer prescription) {
        this.prescription = prescription;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
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

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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

    public List<DocumentDetails> getDocumentDetailsList() {
        return documentDetailsList;
    }

    public void setDocumentDetailsList(List<DocumentDetails> documentDetailsList) {
        this.documentDetailsList = documentDetailsList;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public PrevConsultations(Timestamp startTime, Timestamp endTime, String doctorName, String patientName, int doctorId, int consultId, String specialization, List<DocumentDetails> documentDetailsList, Integer prescription, Date followUpDate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.consultId = consultId;
        this.specialization = specialization;
        this.documentDetailsList = documentDetailsList;
        this.prescription = prescription;
        this.followUpDate = followUpDate;
    }
}
