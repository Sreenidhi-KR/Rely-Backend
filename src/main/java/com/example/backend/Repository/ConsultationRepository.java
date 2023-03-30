package com.example.backend.Repository;

import com.example.backend.Bean.Consultation;
import com.example.backend.Bean.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

//    @Query(value="select * from consultation a where a.id= :id", nativeQuery=true)
//    Consultation findConsultationByCid(int id);

    Consultation findConsultationById(int id);


    //List<Documents> getAllDocumentsById(Consultation consultation);
}
