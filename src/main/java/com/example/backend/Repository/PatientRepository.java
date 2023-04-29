package com.example.backend.Repository;


import com.example.backend.Bean.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Long> {


    @Modifying
    @Query(value="update patient a set a.is_active=false where a.Id= :patient_id", nativeQuery=true)
    void deletePatientById (Integer patient_id);

    Patient findPatientById (Integer patientId);

    @Query(value="select * from patient a where a.user_id= :userId and a.is_active=true", nativeQuery=true)
    List<Patient> getPatients (Integer userId);

    @Modifying
    @Query(value="update Patient u set u.fname=:fname,u.lname=:lname,u.DOB=:dob,u.sex=:sex,u.blood_group=:bloodGroup,u.city=:city,u.state=:state,u.pinCode=:pinCode,u.relationship=:relationship where u.id=:patientId")
    void updatePatient (Integer patientId, String fname, String lname, String dob, char sex, String bloodGroup, String city, String state, String pinCode, String relationship);
}

