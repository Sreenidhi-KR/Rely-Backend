package com.example.backend.Service;

import com.example.backend.Bean.Doctor;
import com.example.backend.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DoctorService {



    @Autowired
    private DoctorRepository doctorRepository;

    @Resource(name = "DQueueService")
    public DQueueService dQueueService;


    public List<Doctor> list() {
        return doctorRepository.findAll();
    }
    public void addDoctor(Doctor doctor){
        Doctor s = doctorRepository.save(doctor);
        dQueueService.createDQueueForDoctorId(s.getId());
    }
    public void deleteDoctorById(int doctor_id){ doctorRepository.deleteDoctorById(doctor_id);}
    public void updateDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }
    public List<Doctor> listBySpec(String specialization) { return doctorRepository.findDocBySpec(specialization);}
    public Doctor findById(int doctor_id) { return doctorRepository.findDocById(doctor_id);}
}
