package com.example.backend.Repository;

import java.util.Optional;

import com.example.backend.Bean.RefreshTokenDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.backend.Bean.RefreshToken;
import com.example.backend.Bean.User;
import com.example.backend.Bean.Doctor;

@Repository
public interface RefreshTokenDoctorRepository extends JpaRepository<RefreshTokenDoctor, Long> {
    Optional<RefreshTokenDoctor> findByToken(String token);

}
