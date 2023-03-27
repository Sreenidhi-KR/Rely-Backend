package com.example.backend.Repository;

import com.example.backend.Bean.DQueue;
import com.example.backend.Bean.Doctor;
import com.example.backend.Bean.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DQueueRepository extends JpaRepository<DQueue, Long> {
//    @Query(value="select patientList from DQueue q where q.id= :queueID", nativeQuery=true)
//    List<Patient> getAllPatientsFromDQueue(int queueID);
//
//    @Query("SELECT q.patientList FROM DQueue q WHERE q.doctorId = :doctor")
//    List<Patient> getAllPatientsFromQueue(@Param("doctor") Integer doctorId);
//
//    @Query(value = "select *  FROM DQueue q WHERE q.doctorId = :doctor" , nativeQuery=true)
//    DQueue findByDoctorId(Integer doctor);

    DQueue findDQueueByDoctor(Doctor doctor);


}

