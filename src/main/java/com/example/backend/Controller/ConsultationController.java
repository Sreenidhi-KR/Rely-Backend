package com.example.backend.Controller;

import com.example.backend.Bean.Documents;
import com.example.backend.DocumentDetails;
import com.example.backend.Service.ConsultationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/consultation")
public class ConsultationController {
    @Resource(name = "consultationService")
    private ConsultationService consultationService;

    @GetMapping("/getAllDocumentsByCid/{Cons_id}")
    public List<DocumentDetails> getAllDocuments(@PathVariable int Cons_id) {
        return consultationService.getAllDocuments(Cons_id);
    }

    @GetMapping("/addDocumentByCid_Docuid/{Cons_id}/{Docu_id}")
    public void addDocumentByCidDid(@PathVariable int Cons_id, @PathVariable int Docu_id){
        consultationService.addDocument(Cons_id, Docu_id);
    }

    @GetMapping("/removeDocumentByCid_Docuid/{Cons_id}/{Docu_id}")
    public void removeDocumentByCidDid(@PathVariable int Cons_id, @PathVariable int Docu_id){
        consultationService.removeDocument(Cons_id, Docu_id);
    }
}
