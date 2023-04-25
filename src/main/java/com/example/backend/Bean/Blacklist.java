package com.example.backend.Bean;

import javax.persistence.*;

@Entity
@Table(name="Blacklist")
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    int Id;

    @Column(name="JwtToken")
    String JwtToken;

    public String getJwtToken() {
        return JwtToken;
    }

    public void setJwtToken(String jwtToken) {
        JwtToken = jwtToken;
    }

    public Blacklist(String jwtToken) {
        this.JwtToken = jwtToken;
    }

    public Blacklist(){}
}
