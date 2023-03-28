package com.example.backend.Controller;

import com.example.backend.Bean.Patient;
import com.example.backend.Service.DQueueService;
import com.example.backend.Service.DoctorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/dqueue")
public class DQueueController {

    @Resource(name = "DQueueService")
    public DQueueService dQueueService;

    @GetMapping("/getPatients/{doctorId}")
    public List<Patient> getAllPatientsFromDQueue(@PathVariable int doctorId){
        return dQueueService.getAllPatientsFromDQueue(doctorId);
    }

    @GetMapping("/addPatient/{doctorId}/{patientId}")
    public void addPatientToDqueue(@PathVariable int doctorId , @PathVariable int patientId){
        dQueueService.addPatientToQueue(doctorId,patientId);
    }

    @GetMapping("/removePatient/{doctorId}/{patientId}")
    public void removePatientToDqueue(@PathVariable int doctorId , @PathVariable int patientId){
        dQueueService.removePatientFromQueue(doctorId,patientId);
    }

    @GetMapping("/getPatientIndex/{doctorId}/{patientId}")
    public int getPatientIndex(@PathVariable int doctorId , @PathVariable int patientId){
        return dQueueService.getPatientIndexFromQueue(doctorId,patientId);
    }

}
