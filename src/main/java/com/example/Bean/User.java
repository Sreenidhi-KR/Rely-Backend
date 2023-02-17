package com.example.Bean;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name="User")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="Id")
    private int id;

    @Column(name="Fname")
    private String fname;

    @Column(name="Lname")
    private String lname;

    @Column(name="PhoneNo")
    private String phone_no;

    @Column(name="Email")
    private String email;

    @Column(name="PhotoURL")
    private String photo_url;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private ArrayList<Patient> profiles;

    public ArrayList<Patient> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Patient> profiles) {
        this.profiles = profiles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}
