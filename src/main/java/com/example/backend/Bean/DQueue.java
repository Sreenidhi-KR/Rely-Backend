package com.example.backend.Bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="DQueue")
public class DQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne //over
    @JoinColumn(name="DoctorId", referencedColumnName = "Id" , nullable = false)
    private Doctor doctor;

    @OneToMany(targetEntity = Patient.class, cascade = CascadeType.ALL, fetch=FetchType.LAZY) //over
    private List<Patient> patientList = new ArrayList<>();

    @OneToMany(targetEntity = Consultation.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY) //over
    private List<Consultation> consultationList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsultationList(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }

    public DQueue(Doctor doctor, List<Patient> patientList, List<Consultation> consultationList) {
        this.doctor = doctor;
        this.patientList = patientList;
        this.consultationList = consultationList;
    }

    public DQueue( Doctor doctor) {
        this.doctor = doctor;
    }

    public DQueue() {
    }
}