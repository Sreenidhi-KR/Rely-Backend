package com.example.backend.Service;

import com.example.backend.Bean.Doctor;
import com.example.backend.Bean.User;
import com.example.backend.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> list() {
        return doctorRepository.findAll();
    }
    public List<Doctor> listBySpec(String specialization) { return doctorRepository.findDocBySpec(specialization);}
}
