package com.example.backend.Service;

import com.example.backend.Bean.Patient;
import com.example.backend.Bean.Role;
import com.example.backend.Bean.User;
import com.example.backend.Repository.PatientRepository;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients(int userId){
        User user = userRepository.findUserById(userId);
        //System.out.println("working file till step 1");
        List<Patient> patients = patientRepository.getPatients(userId);
        //System.out.println("working fine till step 2");
        return patients;
    }

    public void addPatient(Patient patient, int userId){
        //get user with the given id
        User user=userRepository.findUserById(userId);
        //add the new patient profile to the list of profiles and update
        List<Patient>patients = user.getProfiles();
        if (patients==null){
            patients=new ArrayList<>();
        }
        patient.setUser(user);
        patients.add(patient);
        user.setProfiles(patients);
        userRepository.save(user);
    }

    public void removePatient(int patientId){
        patientRepository.deletePatientById(patientId);
    }

}