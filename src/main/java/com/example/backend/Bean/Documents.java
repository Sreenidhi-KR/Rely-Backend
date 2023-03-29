package com.example.backend.Bean;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Documents")
public class Documents{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="Name")
    private String name;

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


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){return name;}

    public void setName(String name){this.name=name;
    }
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public String getDocument_type() {
        return this.document_type;
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

    public Documents(Date date_time, String document_type, byte[] data, Patient patient,String name) {
        //this.Id = id;
        this.date_time = date_time;
        this.document_type = document_type;
        this.data = data;
        this.patient = patient;
        this.name=name;
    }

    public Documents(String name)
    {
        this.name = name;
    }

    public Documents() {
    }
}