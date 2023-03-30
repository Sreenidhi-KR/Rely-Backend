package com.example.backend.Repository;

import com.example.backend.Bean.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Integer>{

    @Query(value="select * from documents a where a.patient_id= :id", nativeQuery=true)
    List<Documents> getAll(int id);

    Documents findDocumentsById(int documentid);
}
