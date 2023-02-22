package com.example.backend.Bean;

import javax.persistence.*;
import java.util.List;

@Entity
public class Doctor_queue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(cascade = CascadeType.ALL) //over
    @JoinColumn(name="DoctorId", referencedColumnName = "Id")
    private Doctor doctor_id;

    @OneToMany(targetEntity = Patient.class, mappedBy = "Id", cascade = CascadeType.ALL, fetch=FetchType.LAZY) //over
    private List<Patient> patient_list;

    @OneToMany(targetEntity = Consultation.class, mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //over
    private List<Consultation> consultation_list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Doctor doctor_id) {
        this.doctor_id = doctor_id;
    }

    public List<Patient> getPatient_list() {
        return patient_list;
    }

    public void setPatient_list(List<Patient> patient_list) {
        this.patient_list = patient_list;
    }

    public List<Consultation> getConsultation_list() {
        return consultation_list;
    }

    public void setConsultation_list(List<Consultation> consultation_list) {
        this.consultation_list = consultation_list;
    }

    public Doctor_queue(int id, Doctor doctor_id, List<Patient> patient_list, List<Consultation> consultation_list) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.patient_list = patient_list;
        this.consultation_list = consultation_list;
    }

    public Doctor_queue() {
    }
}