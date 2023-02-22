package com.example.backend.Repository;

import com.example.backend.Bean.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(value="select * from doctor a where a.specialization= :specialization", nativeQuery=true)
    List<Doctor> findDocBySpec(String specialization);
    }

