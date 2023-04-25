package com.example.backend.Repository;

import com.example.backend.Bean.Blacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    @Query(value="select * from blacklist a where a.jwt_token=:token", nativeQuery=true)
    public String isTokenAvailable(String token);
}
