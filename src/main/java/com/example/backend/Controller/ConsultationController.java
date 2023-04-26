package com.example.backend.Controller;

import com.example.backend.Bean.Consultation;
import com.example.backend.Bean.DocumentDetails;
import com.example.backend.Bean.FollowUp;
import com.example.backend.Bean.PrevConsultations;
import com.example.backend.Service.ConsultationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    public List<DocumentDetails> getAllDocuments(@PathVariable Integer consultationId) throws Exception{
        try {
            return consultationService.getAllDocumentDetails(consultationId);
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/addDocumentByCid_Docuid/{consultationId}/{documentId}")
    public void addDocumentByCidDid(@PathVariable Integer consultationId, @PathVariable Integer documentId) throws Exception{
        try{
            consultationService.addDocument(consultationId, documentId);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    @GetMapping("/addDocumentByCid_PrescriptionId/{consultationId}/{prescriptionId}")
    public void addDocumentByCidPid(@PathVariable Integer consultationId, @PathVariable Integer prescriptionId) throws Exception{
        try{
            consultationService.addDocument(consultationId, prescriptionId);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    @GetMapping("/removeDocumentByCid_Docuid/{consultationId}/{documentId}")
    public void removeDocumentByCidDid(@PathVariable Integer consultationId, @PathVariable Integer documentId) throws Exception{
        try{
            consultationService.removeDocument(consultationId, documentId);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    @GetMapping("/getPrevConsultations/{patientId}")
    public List<PrevConsultations> getPrevConsultationsByPid(@PathVariable Integer patientId) throws Exception{
        try{
            return consultationService.getPrevConsultations(patientId);
        }catch (Exception e)
        {
            System.out.println(e);
            return  null;
        }
    }

    @GetMapping("/getPrevConsultationsDoctor/{doctorId}")
    public List<PrevConsultations> getPrevConsultationsByDid(@PathVariable Integer doctorId) throws Exception{
        try{
            return consultationService.getPrevConsultationsDoctor(doctorId);
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getPrevConsultationsStats/{doctorId}")
    @ResponseBody
    public List<Map<String,Long>> getPrevConsultationsStats(@PathVariable Integer doctorId) throws Exception{
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
    public Integer addConsulation(@RequestBody Consultation consultation) throws Exception{
        try{
            return consultationService.addConsultation(consultation);
        } catch(Exception e){
            System.out.println(e);
            return -1;
        }
    }

    @PostMapping("/updateConsultationEndTime")
    public void updateConsultationEndTime(@RequestBody Consultation consultation) throws Exception{
        try{
            consultationService.updateConsultationEndtime(consultation);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    @PostMapping("/setFollowUp/{consultation_id}/{followUpDate}")
    public void setFollowUpDate(@PathVariable Integer consultation_id, @PathVariable String followUpDate) throws Exception{
        try {
            Date date = new SimpleDateFormat("yyyyy-MM-dd").parse(followUpDate);
            //Date followUpDate = new Date();
            consultationService.setFollowUpDate(consultation_id, date);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/getFollowUp/{patient_id}")
    public List<FollowUp> getFollowUp(@PathVariable Integer patient_id)throws Exception{
        try{
            return consultationService.getFollowUp(patient_id);
        }
        catch(Exception e)
        {
            System.out.println(e);
            return  null;
        }
    }
}
