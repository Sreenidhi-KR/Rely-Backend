package com.example.backend.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Documents")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private int Id;

    @Column(name="DateTime")
    private Date date_time;

    @Column(name="DocumentType")
    private String document_type;

    @Lob
    @Column(name="Data")
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY) //Over
    @JoinColumn(name="patient_id", referencedColumnName = "Id", updatable = true, insertable = true)
    private Patient patient;

//    @ManyToMany(targetEntity = Consultation.class,cascade = CascadeType.ALL,mappedBy = "consultations",fetch = FetchType.LAZY) //Over
//    private List<Consultation> consultations;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data= data;
    }

    public Documents(int id, Date date_time, String document_type, byte[] data, Patient patient) {
        Id = id;
        this.date_time = date_time;
        this.document_type = document_type;
        this.data = data;
        this.patient = patient;
    }

    public Documents() {
    }
}