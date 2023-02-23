package com.example.backend.Service;

import com.example.backend.Bean.Admin;
import com.example.backend.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Boolean login(Admin admin){
        Admin newAdmin = adminRepository.findByUsername(admin.getUsername());
        if(admin.getPassword().equals(newAdmin.getPassword())) return Boolean.TRUE;
        else return Boolean.FALSE;
    }
}
