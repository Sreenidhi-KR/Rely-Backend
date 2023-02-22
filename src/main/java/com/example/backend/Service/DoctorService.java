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
    public void addDoctor(Doctor doctor){ doctorRepository.save(doctor);}
    public void deleteDoctorById(int doctor_id){ doctorRepository.deleteDoctorById(doctor_id);}
    public void updateDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }
    public List<Doctor> listBySpec(String specialization) { return doctorRepository.findDocBySpec(specialization);}
}
