package com.example.backend.Service;

import com.example.backend.Bean.Consultation;
import com.example.backend.Bean.Doctor;
import com.example.backend.Bean.Documents;
import com.example.backend.Bean.PrevConsultations;
import com.example.backend.DocumentDetails;
import com.example.backend.Repository.ConsultationRepository;
import com.example.backend.Repository.DoctorRepository;
import com.example.backend.Repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<DocumentDetails> getAllDocuments(int Cons_id) {
        Consultation consultation = consultationRepository.findConsultationById(Cons_id);
        Set<Documents> docs = consultation.getDocuments();
        List<DocumentDetails> consultationDocuments = new ArrayList<>();
        for(Documents d : docs){
            int document_id = d.getId();
            String document_name = d.getName();
            DocumentDetails temp = new DocumentDetails(document_id, document_name);
            consultationDocuments.add(temp);
        }
        return consultationDocuments;
    }

    public void addDocument(int consultationid, int documentid)
    {
        System.out.println("Starting Api");
        Consultation consultation = consultationRepository.findConsultationById(consultationid);
        System.out.println(consultation);
        Set<Documents> docs = consultation.getDocuments();
        System.out.println(docs);
        Documents document = documentsRepository.findDocumentsById(documentid);
        System.out.println(document.getId());
        if(!docs.contains(document)) {
            if (docs == null) {
                docs = new HashSet<>();
            }
            docs.add(document);
        }
        consultation.setDocuments(docs);
        consultationRepository.save(consultation);
    }

    public void removeDocument(int consultationid, int documentid)
    {
        Consultation consultation = consultationRepository.findConsultationById(consultationid);
        Set<Documents> docs = consultation.getDocuments();
        Documents document = documentsRepository.findDocumentsById(documentid);
        if(docs.contains(document)){
            docs.remove(document);
            consultation.setDocuments(docs);
            consultationRepository.save(consultation);
        }
        else{
            throw new IllegalArgumentException("Document Not Found to be Removed");
        }
    }

    public List<PrevConsultations> getPrevConsultations(int patientId)
    {
        List<Consultation> allConsultations = consultationRepository.getAllConsultationsByPid(patientId);
        List<PrevConsultations> all_consults = new ArrayList<>();
        for(Consultation consult: allConsultations ) {
            Timestamp start = consult.getStart_time();
            Timestamp end = consult.getEnd_time();
            int doctor_id = consult.getDoctor_id();
            Doctor doc = doctorRepository.findDocById(doctor_id);
            String doc_name = doc.getFname() + " " + doc.getLname();
            int consult_id = consult.getId();
            String specialization = doc.getSpecialization();
            PrevConsultations individual_consultation = new PrevConsultations(start, end, doc_name, consult_id, specialization);
            all_consults.add(individual_consultation);
        }
        return all_consults;
    }


}
