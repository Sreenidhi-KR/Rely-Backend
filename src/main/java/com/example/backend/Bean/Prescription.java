package com.example.backend.Bean;

import javax.persistence.*;

@Entity
@Table(name="Prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int id;

    @Column(name="PrescriptionURL")
    private String prescription_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrescription_url() {
        return prescription_url;
    }

    public void setPrescription_url(String prescription_url) {
        this.prescription_url = prescription_url;
    }

    public Prescription(int id, String prescription_url) {
        this.id = id;
        this.prescription_url = prescription_url;
    }

    public Prescription() {
    }
}