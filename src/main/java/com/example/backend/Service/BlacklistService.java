package com.example.backend.Service;

import com.example.backend.Bean.Blacklist;
import com.example.backend.Repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {

    @Autowired
    BlacklistRepository blacklistRepository;
    public void setToken(Blacklist token){
        blacklistRepository.save(token);
    }

    public boolean isTokenAvailable(String token){
        String bearer= "Bearer "+token;
        String a=blacklistRepository.isTokenAvailable(bearer);
        System.out.println("adasdasasasassa"+a);
        if(a!=null){
            return true;
        }
        else{
            return false;
        }
    }
}
