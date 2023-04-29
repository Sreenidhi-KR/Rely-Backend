package com.example.backend.Bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="DQueue")
public class DQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="DoctorAcceptance")
    private boolean accept;

    @OneToOne //over
    @JoinColumn(name="DoctorId", referencedColumnName = "Id" , nullable = false)
    private Doctor doctor;

    @OneToMany(targetEntity = Patient.class, cascade = CascadeType.ALL, fetch=FetchType.LAZY) //over
    @OrderColumn
    private List<Patient> patientList = new ArrayList<>();

    @OneToOne(targetEntity = Consultation.class, cascade = CascadeType.ALL) //over
    private Consultation consultation;

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public DQueue(Doctor doctor, List<Patient> patientList, Consultation consultation) {
        this.doctor = doctor;
        this.patientList = patientList;
        this.consultation = consultation;
        this.accept=false;
    }

    public DQueue( Doctor doctor) {
        this.doctor = doctor;
    }

    public DQueue() {
    }
}