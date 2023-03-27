package com.example.backend.Repository;

import com.example.backend.Bean.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Integer>{

}
