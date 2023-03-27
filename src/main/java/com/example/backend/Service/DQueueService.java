package com.example.backend.Service;

import com.example.backend.Bean.DQueue;
import com.example.backend.Bean.Doctor;
import com.example.backend.Bean.Patient;
import com.example.backend.Repository.DQueueRepository;
import com.example.backend.Repository.DoctorRepository;
import com.example.backend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DQueueService {

    @Autowired
    private DQueueRepository dQueueRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public void createDQueue(DQueue queue){ dQueueRepository.save(queue);}

    public void createDQueueForDoctorId(int doctorId){
        Doctor find_doctor = doctorRepository.findDocById(doctorId);
        DQueue dQueue = new DQueue(find_doctor);
        dQueueRepository.save(dQueue);
    }

    public void hello(){
        System.out.println("Hello from Dqueue Service");
    }

    public List<Patient> getAllPatientsFromDQueue(int doctorId) {
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        return queue.getPatientList();
    }

    public void addPatientToQueue(Integer doctorId, Integer patientId) {
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        Patient patient = patientRepository.findPatientById(patientId);
        if (queue == null) {
            throw new IllegalArgumentException("No queue found for doctor");
        }
        List<Patient> patientList = queue.getPatientList();
        if(patientList == null){
            patientList = new ArrayList<>();
        }
        patientList.add(patient);
        dQueueRepository.save(queue);
    }
    public void removePatientFromQueue(Integer doctorId, Integer patientId) {
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        Patient patient = patientRepository.findPatientById(patientId);
        if (queue == null) {
            throw new IllegalArgumentException("No queue found for doctor");
        }
        List<Patient> patientList = queue.getPatientList();
        if(patientList.contains(patient)) {
            patientList.remove(patient);
        }
        dQueueRepository.save(queue);
    }

    public Integer getPatientIndexFromQueue(Integer doctorId, Integer patientId){
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        Patient patient = patientRepository.findPatientById(patientId);
        if (queue == null) {
            throw new IllegalArgumentException("No queue found for doctor");
        }
        List<Patient> patientList = queue.getPatientList();
        if(!patientList.contains(patient)){
            return -1;
        }
        return patientList.indexOf(patient)+1;
    }
}
