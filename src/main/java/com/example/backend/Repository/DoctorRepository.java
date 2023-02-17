package com.example.backend.Repository;

import com.example.backend.Bean.Doctor;
import com.example.backend.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
