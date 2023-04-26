package com.example.backend.Bean;

import javax.persistence.*;

@Entity
@Table(name="Prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="PrescriptionURL")
    private String prescription_url;

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getPrescription_url() {
        return prescription_url;
    }

    public void setPrescription_url(String prescription_url) {
        this.prescription_url = prescription_url;
    }

    public Prescription (Integer id, String prescription_url) {
        this.id = id;
        this.prescription_url = prescription_url;
    }

    public Prescription() {
    }
}