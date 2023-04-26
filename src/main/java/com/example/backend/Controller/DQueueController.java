package com.example.backend.Controller;

import com.example.backend.Bean.DQueue;
import com.example.backend.Bean.Doctor;
import com.example.backend.Bean.Patient;
import com.example.backend.Payload.Response.DQueueInfo;
import com.example.backend.Service.DQueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<Patient> getAllPatientsFromDQueue(@PathVariable Integer doctorId){
        return dQueueService.getAllPatientsFromDQueue(doctorId);
    }

    @GetMapping("/addPatient/{doctorId}/{patientId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void addPatientToDqueue(@PathVariable Integer doctorId , @PathVariable Integer patientId){
        dQueueService.addPatientToQueue(doctorId,patientId);
    }

    @GetMapping("/removePatient/{doctorId}/{patientId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void removePatientToDqueue(@PathVariable Integer doctorId , @PathVariable Integer patientId){
        dQueueService.removePatientFromQueue(doctorId,patientId);
    }

    @GetMapping("/getPatientIndex/{doctorId}/{patientId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public DQueueInfo getPatientIndex(@PathVariable Integer doctorId , @PathVariable Integer patientId){
        return dQueueService.getPatientIndexFromQueue(doctorId,patientId);
    }

    @GetMapping("/getDqueue/{doctorId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public Integer getDqueueId(@PathVariable Integer doctorId){
        return dQueueService.getDqueueId(doctorId);
    }

    @GetMapping("/getConsultationId/{qid}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public Integer getConsulationId(@PathVariable Integer qid){
        return dQueueService.getConsultationId(qid);
    }

    @GetMapping("/getPatientId/{qid}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public Integer getPatientId(@PathVariable Integer qid){
        return dQueueService.getPatientId(qid);
    }

    @GetMapping("/getQuickDoctor")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getQuickDoctor(){
        Doctor d = dQueueService.getQuickDoctor();
        if(d==null)
        {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(d, HttpStatus.OK);
    }

    @GetMapping("/removeAllPatient/{doctorId}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void removeAllPatientFromDqueue(@PathVariable Integer doctorId){
        Integer qid=dQueueService.getDqueueId(doctorId);
        dQueueService.removeAllPatientsFromQueue(qid);
    }

    @GetMapping("/toggleAcceptPatient/{queueId}/{bool}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public boolean toggleAcceptPatient(@PathVariable Integer queueId,@PathVariable boolean bool){
        return dQueueService.toggleAcceptPatient(queueId,bool);
    }

}
