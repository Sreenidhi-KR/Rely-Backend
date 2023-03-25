package com.example.backend.Bean;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="USER")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    @OneToMany(targetEntity = Patient.class, mappedBy = "Id", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //over
    private List<Patient> profiles;

    public User(){
    }
    public User(int id, String fname, String lname, String phone_no, String email, String photo_url, List<Patient> profiles) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.phone_no = phone_no;
        this.email = email;
        this.photo_url = photo_url;
        this.profiles = profiles;
    }

    public List<Patient> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Patient> profiles) {
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