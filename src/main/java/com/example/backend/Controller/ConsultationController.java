package com.example.backend.Controller;

import com.example.backend.Bean.Consultation;
import com.example.backend.Bean.DocumentDetails;
import com.example.backend.Bean.PrevConsultations;
import com.example.backend.Service.ConsultationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/consultation")
public class ConsultationController {
    @Resource(name = "consultationService")
    private ConsultationService consultationService;

    @GetMapping("/getAllDocumentsByCid/{consultationId}")
    public List<DocumentDetails> getAllDocuments(@PathVariable int consultationId) {
        return consultationService.getAllDocumentDetails(consultationId);
    }

    @GetMapping("/addDocumentByCid_Docuid/{consultationId}/{documentId}")
    public void addDocumentByCidDid(@PathVariable int consultationId, @PathVariable int documentId){
        consultationService.addDocument(consultationId, documentId);
    }

    @GetMapping("/addDocumentByCid_PrescriptionId/{consultationId}/{prescriptionId}")
    public  void addDocumentByCidPid(@PathVariable int consultationId, @PathVariable int prescriptionId){
        consultationService.addDocument(consultationId, prescriptionId);
    }

    @GetMapping("/removeDocumentByCid_Docuid/{consultationId}/{documentId}")
    public void removeDocumentByCidDid(@PathVariable int consultationId, @PathVariable int documentId){
        consultationService.removeDocument(consultationId, documentId);
    }

    @GetMapping("/getPrevConsultations/{patientId}")
    public List<PrevConsultations> getPrevConsultationsByPid(@PathVariable int patientId){
        return consultationService.getPrevConsultations(patientId);
    }

    @GetMapping("/getPrevConsultationsDoctor/{doctorId}")
    public List<PrevConsultations> getPrevConsultationsByDid(@PathVariable int doctorId){
        return consultationService.getPrevConsultationsDoctor(doctorId);
    }

    @GetMapping("/getPrevConsultationsStats/{doctorId}")
    @ResponseBody
    public List<Map<String,Long>> getPrevConsultationsStats(@PathVariable int doctorId){
        List<Map<String,Long>> arr=new ArrayList<>();
        try {
            arr = consultationService.getPrevConsultationsStats(doctorId);
            System.out.println(arr);
            return arr;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return arr;
    }
    @PostMapping("/addConsultation")
    public int addConsulation(@RequestBody Consultation consultation){
        return consultationService.addConsultation(consultation);
    }

    @PostMapping("/updateConsultationEndTime")
    public void updateConsultationEndTime(@RequestBody Consultation consultation){
        consultationService.updateConsultationEndtime(consultation);
    }

    @PostMapping("/setFollowUp/{consultation_id}/{followUpDate}")
    public void setFollowUpDate(@PathVariable int consultation_id, @PathVariable Date followUpDate){
            //Date followUpDate = new Date();
            consultationService.setFollowUpDate(consultation_id, followUpDate);
    }

}
