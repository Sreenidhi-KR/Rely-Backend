package com.example.backend.Controller;

import com.example.backend.Bean.Patient;
import com.example.backend.Service.DQueueService;
import com.example.backend.Service.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<Patient> getAllPatientsFromDQueue(@PathVariable int doctorId){
        return dQueueService.getAllPatientsFromDQueue(doctorId);
    }

    @GetMapping("/addPatient/{doctorId}/{patientId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void addPatientToDqueue(@PathVariable int doctorId , @PathVariable int patientId){
        dQueueService.addPatientToQueue(doctorId,patientId);
    }

    @GetMapping("/removePatient/{doctorId}/{patientId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void removePatientToDqueue(@PathVariable int doctorId , @PathVariable int patientId){
        dQueueService.removePatientFromQueue(doctorId,patientId);
    }

    @GetMapping("/getPatientIndex/{doctorId}/{patientId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public int getPatientIndex(@PathVariable int doctorId , @PathVariable int patientId){
        return dQueueService.getPatientIndexFromQueue(doctorId,patientId);
    }

    @GetMapping("/getDqueue/{doctorId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public int getDqueueId(@PathVariable int doctorId){
        return dQueueService.getDqueueId(doctorId);
    }

    @GetMapping("/getConsultationId/{qid}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public Integer getConsulationId(@PathVariable int qid){
        return dQueueService.getConsultationId(qid);
    }

    @GetMapping("/getPatientId/{qid}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public Integer getPatientId(@PathVariable int qid){
        return dQueueService.getPatientId(qid);
    }

}
