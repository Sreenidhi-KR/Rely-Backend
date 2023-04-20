package com.example.backend.Service;

import com.example.backend.Bean.*;
import com.example.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ConsultationService {

    @Autowired
    private DQueueRepository dQueueRepository;
    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private PatientRepository patientRepository;

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
        Patient p = patientRepository.findPatientById(patientId);
        String patient_name = p.getFname() + " " + p.getLname();
        List<Consultation> allConsultations = consultationRepository.getAllConsultationsByPid(patientId);
        List<PrevConsultations> all_consults = new ArrayList<>();
        for(Consultation consult: allConsultations ) {
            Timestamp start = consult.getStart_time();
            Timestamp end = consult.getEnd_time();
            Integer doctor_id = consult.getDoctor_id();
            Doctor doc = doctorRepository.findDocById(doctor_id);
            String doc_name = doc.getFname() + " " + doc.getLname();
            Integer consult_id = consult.getId();
            Date followUp = consultationRepository.getFollowUpDate(consult_id);
            String specialization = doc.getSpecialization();
            List<DocumentDetails> documentDetailsList = getAllDocumentDetails(consult_id);
            Integer pid= consult.getPrescription_id();
            PrevConsultations individual_consultation = new PrevConsultations(start, end, doc_name, patient_name, consult_id, specialization,documentDetailsList,pid,followUp);
            all_consults.add(individual_consultation);
        }
        return all_consults;
    }

    public List<PrevConsultations> getPrevConsultationsDoctor(int doctor_Id)
    {
        Doctor d = doctorRepository.findDocById(doctor_Id);
        String doc_name=d.getFname()+" "+d.getLname();
        List<Consultation> allConsultations = consultationRepository.getAllConsultationsByDid(doctor_Id);
        List<PrevConsultations> all_consults = new ArrayList<>();
        for(Consultation consult: allConsultations ) {
            Timestamp start = consult.getStart_time();
            Timestamp end = consult.getEnd_time();
            Integer patient_id = consult.getPatient_id();
            Patient p = patientRepository.findPatientById(patient_id);
            String patient_name = p.getFname() + " " + p.getLname();
            Integer consult_id = consult.getId();
            Date followUp = consultationRepository.getFollowUpDate(consult_id);
            List<DocumentDetails> documentDetailsList = getAllDocumentDetails(consult_id);
            Integer pid= consult.getPrescription_id();
            PrevConsultations individual_consultation = new PrevConsultations(start, end, doc_name, patient_name, consult_id, null,documentDetailsList,pid,followUp);
            all_consults.add(individual_consultation);
        }
        return all_consults;
    }

    public List<Map<String,Long>> getPrevConsultationsStats(int doctor_Id) throws Exception
    {
        Doctor d = doctorRepository.findDocById(doctor_Id);
        List<Consultation> allConsultations = consultationRepository.getAllConsultationsByDid(doctor_Id);
        HashMap<Long,Integer> stats = new HashMap<Long,Integer>();
        for(Consultation consult: allConsultations ) {
            Date start = consult.getStart_time();
            start.setHours(0);
            start.setMinutes(0);
            start.setSeconds(0);
            System.out.println(start);
            long millis = start.getTime();
            if(stats.containsKey(millis)){
                int val=stats.get(millis).intValue();
                val=val+1;
                stats.remove(millis);
                stats.put(millis,val);
            }
            else{
                stats.put(millis,1);
            }
        }
        List<Map<String,Long>> statistics = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : stats.entrySet()) {
            Map<String,Long> m = new HashMap<>();
            Long key = entry.getKey();
            Long value = (long) entry.getValue();
            m.put("x",key);
            m.put("y",value);
            statistics.add(m);
        }
        return statistics;
    }

    public int addConsultation(Consultation consultation)
    {
        Consultation c = consultationRepository.save(consultation);
        int followupId = c.getFollowup_id();
        if(followupId!=0)
        {
            Consultation prevConsultation = consultationRepository.findConsultationById(followupId);
            Set<Documents> prevDocuments = prevConsultation.getDocuments();
            Set<Documents> copy = new HashSet<>(prevDocuments);
            c.setDocuments(copy);
            consultationRepository.save(c);
        }
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

    public void setFollowUpDate(int consultation_id, Date followUpDate){
        Consultation c = consultationRepository.findConsultationById(consultation_id);
        c.setFollow_up_date(followUpDate);
        consultationRepository.save(c);
    }
}
