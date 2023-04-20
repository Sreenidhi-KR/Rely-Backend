package com.example.backend.Repository;

import com.example.backend.Bean.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin, Long> {
    //Admin findByUsername(String username);

    Optional<Admin> findByUserName(String username);

    Boolean existsByUserName(String username);
}
