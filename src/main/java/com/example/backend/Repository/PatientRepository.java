package com.example.backend.Repository;


import com.example.backend.Bean.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Long> {


    @Modifying
    @Query(value="delete from Patient a where a.Id= :patient_id", nativeQuery=true)
    void deletePatientById(int patient_id);

    Patient findPatientById(int patientId);
}

