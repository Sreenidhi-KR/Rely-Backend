package com.example.backend.Repository;

import com.example.backend.Bean.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Integer>{

    @Query(value="select * from documents a where a.patient_id= :id and a.id not in (select prescription_id from consultation where prescription_id is not null)", nativeQuery=true)
    List<Documents> getAll (Integer id);

    @Query(value="select * from documents a where a.patient_id=:id and a.id in (select prescription_id from consultation where prescription_id is not null)",nativeQuery = true)
    List<Documents> getAllPrescriptions (Integer id);

    Documents findDocumentsById (Integer documentid);

    @Transactional
    @Modifying
    @Query(value="update documents set data=NULL where documents.id=:documentId",nativeQuery = true)
    void removeDocumentById (Integer documentId);
}
