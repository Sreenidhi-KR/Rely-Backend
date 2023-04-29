package com.example.backend.Bean;
import javax.persistence.*;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id", nullable = false)
    private Integer id;

    @Column(name="Fname")
    private String fname;

    @Column(name="Lname")
    private String lname;

    @Column(name="DOB")
    private Date DOB;

    @Column(name="Sex")
    private char sex;

    @Column(name="Specialization")
    private String specialization;

    @Column(name="Qualification")
    private String qualification;

    @Column(name="Description")
    private String description;

    @Column(name="Rating")
    private float rating;

    @Column(name="NoOfRatings")
    private Integer no_of_ratings;

    @Column(name="AvailableTimings")
    private String available_timings;

    @Column(name="City")
    private String city;

    @Column(name="State")
    private String state;


    @Column(name="ClinicAddress")
    private String clinic_address;

    @Column(name="PhotoUrl")
    private String photo_url;

    @Column(name="OnlineStatus")
    private boolean online_status;

    @Column(name="ChannelName")
    private String channel_name;

    @Column(name="Token")
    private String token;

    @Column(name="UserName")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="Email")
    private String email;

    @Column(name="Age")
    private Integer age;

    @Column(name="QueueLimit")
    private Integer limit;

    @Column(name="PhoneNumber")
    private Long phoneNo;

    @Column(name="Active")
    private boolean active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "doctor_roles",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId (Integer id) {
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

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Integer getNo_of_ratings() {
        return no_of_ratings;
    }

    public void setNo_of_ratings (Integer no_of_ratings) {
        this.no_of_ratings = no_of_ratings;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAvailable_timings() {
        return available_timings;
    }

    public void setAvailable_timings(String available_timings) {
        this.available_timings = available_timings;
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

    public String getClinic_address() {
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public boolean isOnline_status() {
        return online_status;
    }

    public void setOnline_status(boolean online_status) {
        this.online_status = online_status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean act) {
        active = act;
    }

    public Doctor(String fname, String lname, Date DOB, char sex, String channel_name, String specialization, String qualification, String description, String available_timimgs, String city, String state, String clinic_address, String photo_url, boolean online_status, String userName, String password, String email, String token, Long phoneNo) {
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        this.sex = sex;
        this.specialization = specialization;
        this.qualification = qualification;
        this.description = description;
        this.rating = 0;
        this.available_timings = available_timimgs;
        this.city = city;
        this.state = state;
        this.clinic_address = clinic_address;
        this.photo_url = photo_url;
        this.online_status = online_status;
        this.channel_name = channel_name;
        this.userName=userName;
        this.password=password;
        this.email=email;
        this.token = token;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(DOB);
        this.age=calculateAge(LocalDate.parse(s));
        this.limit=10;
        this.no_of_ratings=0;
        this.phoneNo=phoneNo;
        this.active =true;
    }

    public Doctor(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
        this.limit=10;
    }

    public Doctor() {
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}