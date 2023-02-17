package com.example.backend.Bean;
import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="USER_NAME")
    private String name;
    @Column(name="PHONE_NUMBER")
    private String phone_number;

    public User(){

    }
    public User(Integer id, String name, String phone_number){
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
