package com.example.backend.Bean;

import javax.persistence.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name="Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;

    @Column(name="Fname")
    private String fname;

    @Column(name="Lname")
    private String lname;

    @Column(name="DOB")
    private String DOB;

    @Column(name="Sex")
    private char sex;

    @Column(name="BloodGroup")
    private String blood_group;

    @Column(name="City")
    private String city;

    @Column(name="State")
    private String state;

    @Column(name="PinCode")
    private String pinCode;

    @Column(name="PhotoURL")
    private String photo_url;

    @Column(name="Relationship")
    private String relationship;

    @Column(name="Age")
    private Integer age;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Column(name="isActive")
    private boolean isActive;

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

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
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

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static Integer calculateAge(LocalDate dob) {
        LocalDate curDate = LocalDate.now();
        if ((dob != null) && (curDate != null)) {
            return Period.between(dob, curDate).getYears();
        }
        else {
            return 0;
        }
    }

    public Patient(String fname, String lname, String DOB, char sex, String blood_group, String city, String state, String pinCode, String photo_url, String relationship, List<Documents> documents, User user, boolean isActive) {
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        this.sex = sex;
        this.blood_group = blood_group;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.photo_url = photo_url;
        this.relationship = relationship;
        this.documents = documents;
        this.user = user;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(DOB);
        this.age=calculateAge(LocalDate.parse(s));
        this.isActive=isActive;
    }

    public Patient(String fname ){
        this.fname = fname;
    }
    public Patient() {
    }


}