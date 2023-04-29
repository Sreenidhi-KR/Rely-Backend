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
    public Boolean getOnlineStatus(int doctor_id) { return doctorRepository.getOnlineStatus(doctor_id);}

    public void updateDoctorCall(Doctor doctor){
        Doctor doc=findById(doctor.getId());
        doc.setToken(doctor.getToken());
        doc.setChannel_name(doctor.getChannel_name());
        doc.setOnline_status(doctor.isOnline_status());
        doctorRepository.save(doc);
    }

    public void updateDoctorRating(int doctor_id, int rating){
        Doctor doc = doctorRepository.findDocById(doctor_id);
        int count = doc.getNo_of_ratings();
        doc.setNo_of_ratings(count+1);
        float newRating = ((doc.getRating()*count)+rating)/(count+1);
        doc.setRating(newRating);
        System.out.println(doc.getRating());
        doctorRepository.save(doc);
    }
}
