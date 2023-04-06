package com.example.backend.Service;

import com.example.backend.Bean.*;
import com.example.backend.Repository.ConsultationRepository;
import com.example.backend.Repository.DQueueRepository;
import com.example.backend.Repository.DoctorRepository;
import com.example.backend.Repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConsultationService {

    @Autowired
    private DQueueRepository dQueueRepository;
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<DocumentDetails> getAllDocumentDetails(int consultationId) {
        Consultation consultation = consultationRepository.findConsultationById(consultationId);
        if(consultation == null){
            return new ArrayList<DocumentDetails>();
        }
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

    public void addDocument(int consultationId, int documentId)
    {
        System.out.println("Starting Api");
        Consultation consultation = consultationRepository.findConsultationById(consultationId);
        System.out.println(consultation);
        Set<Documents> docs = consultation.getDocuments();
        System.out.println(docs);
        Documents document = documentsRepository.findDocumentsById(documentId);
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

    public void addPrescription(int consultationid, int documentid)
    {
        System.out.println("Starting Api");
        Consultation consultation = consultationRepository.findConsultationById(consultationid);
        System.out.println(consultation);
        System.out.println("aksjjddn");
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
        consultation.setPrescription_id(documentid);
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
            List<DocumentDetails> documentDetailsList = getAllDocumentDetails(consult_id);
            PrevConsultations individual_consultation = new PrevConsultations(start, end, doc_name, consult_id, specialization,documentDetailsList);
            all_consults.add(individual_consultation);
        }
        return all_consults;
    }

    public int addConsultation(Consultation consultation)
    {
        Consultation c = consultationRepository.save(consultation);
        Integer docId= consultation.getDoctor_id();
        Doctor doctor=doctorRepository.findDocById(docId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        List<Consultation> consultaionList = new ArrayList<>();
        consultaionList.add(c);
        queue.setConsultationList(consultaionList);
        dQueueRepository.save(queue);
        return c.getId();
    }

    public void updateConsultationEndtime(Consultation consultation)
    {
        int id = consultation.getId();
        Timestamp endTime = consultation.getEnd_time();
        consultationRepository.updateConsultationEndTime(id, endTime);
    }

}
