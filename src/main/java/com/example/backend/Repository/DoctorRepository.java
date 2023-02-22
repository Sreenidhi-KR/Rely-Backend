package com.example.backend.Repository;

import com.example.backend.Bean.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(value="select * from doctor a where a.specialization= :specialization", nativeQuery=true)
    List<Doctor> findDocBySpec(String specialization);

    @Modifying
    @Query(value="delete from doctor a where a.id= :doctor_id", nativeQuery=true)
    void deleteDoctorById(int doctor_id);
    }

