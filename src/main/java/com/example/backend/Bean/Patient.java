package com.example.backend.Bean;

import javax.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private int id;

    @Column(name="Fname")
    private String fname;

    @Column(name="Lname")
    private String lname;

    @Column(name="DOB")
    private Date DOB;

    @Column(name="Sex")
    private char sex;

    @Column(name="BloodGroup")
    private String blood_group;

    @Column(name="City")
    private String city;

    @Column(name="State")
    private String state;

    @Column(name="ABDMNum")
    private String abdm_no;

    @Column(name="PhotoURL")
    private String photo_url;

    @Column(name="Relationship")
    private String relationship;

    @OneToMany(targetEntity = Documents.class, mappedBy = "id",cascade = CascadeType.ALL, fetch = FetchType.LAZY) //over
    private List<Documents> documents;

    @ManyToOne(fetch = FetchType.LAZY) //over
    @JoinColumn(name="user_id",referencedColumnName = "id", updatable = true, insertable = true)
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }

    public void addDocument(Documents document){
        this.documents.add(document);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAbdm_no() {
        return abdm_no;
    }

    public void setAbdm_no(String abdm_no) {
        this.abdm_no = abdm_no;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Patient(String fname, String lname, Date DOB, char sex, String blood_group, String city, String state, String abdm_no, String photo_url, String relationship, List<Documents> documents, User user) {
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        this.sex = sex;
        this.blood_group = blood_group;
        this.city = city;
        this.state = state;
        this.abdm_no = abdm_no;
        this.photo_url = photo_url;
        this.relationship = relationship;
        this.documents = documents;
        this.user = user;
    }

    public Patient(String fname ){

        this.fname = fname;
    }



    public Patient() {
    }


}