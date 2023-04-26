package com.example.backend.Repository;

import com.example.backend.Bean.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

//    @Query(value="select * from consultation a where a.id= :id", nativeQuery=true)
//    Consultation findConsultationByCid (Integer id);

    @Query(value="select * from consultation c where c.id= :id", nativeQuery = true)
    Consultation findConsultationById (Integer id);

    @Query(value="select * from consultation c where c.patient_id= :patientid", nativeQuery = true)
    List<Consultation> getAllConsultationsByPid (Integer patientid);

    @Query(value="select * from consultation c where c.doctor_id= :doctorid", nativeQuery = true)
    List<Consultation> getAllConsultationsByDid (Integer doctorid);

    @Query(value="select follow_up from consultation c where c.id= :consultid", nativeQuery = true)
    Date getFollowUpDate (Integer consultid);

    @Modifying
    @Query(value="update consultation c set c.end_time= :endTime where c.id= :id", nativeQuery = true)
    void updateConsultationEndTime (Integer id, Timestamp endTime);

}
