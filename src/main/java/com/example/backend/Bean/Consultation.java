package com.example.backend.Bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="Consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="PatientId")
    private int patient_id;

    @Column(name="DoctorId")
    private int doctor_id;

    @Column(name="StartTime")
    private Timestamp start_time;

    @Column(name="EndTime")
    private Timestamp end_time;

    @ManyToMany(targetEntity = Documents.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) //over
    @JoinTable(name="consultation_documents",
    joinColumns = {
            @JoinColumn(name="consultation_id",referencedColumnName="id",nullable = false,updatable = false)
    },
    inverseJoinColumns ={
            @JoinColumn(name="documents_id",referencedColumnName="id",nullable = false,updatable = false)
    })
    private Set<Documents> documents = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="PrescriptionId", referencedColumnName = "id")
    private Prescription prescription_id;

    @Column(name="FollowUp")
    private Date follow_up_date;

    @ManyToOne(fetch = FetchType.LAZY) //over
    @JoinColumn(name="queue_id", referencedColumnName = "id", updatable = true, insertable = true)
    private Doctor_queue queue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public Set<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<Documents> documents) {
        this.documents = documents;
    }

    public Prescription getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(Prescription prescription_id) {
        this.prescription_id = prescription_id;
    }

    public Date getFollow_up_date() {
        return follow_up_date;
    }

    public void setFollow_up_date(Date follow_up_date) {
        this.follow_up_date = follow_up_date;
    }

    public Doctor_queue getQueue() {
        return queue;
    }

    public void setQueue(Doctor_queue queue) {
        this.queue = queue;
    }

    public Consultation(int id, int patient_id, int doctor_id, Timestamp start_time, Timestamp end_time, Set<Documents> documents, Prescription prescription_id, Date follow_up_date, Doctor_queue queue) {
        this.id = id;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.documents = documents;
        this.prescription_id = prescription_id;
        this.follow_up_date = follow_up_date;
        this.queue = queue;
    }

    public Consultation() {
    }
}