package com.example.backend.Service;

import com.example.backend.Bean.*;
import com.example.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.time.ZoneId;
import java.time.Duration;

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

    public List<DocumentDetails> getAllDocumentDetails (Integer consultationId) {
        Consultation consultation = consultationRepository.findConsultationById(consultationId);
        if(consultation == null){
            return new ArrayList<DocumentDetails>();
        }
        Set<Documents> docs = consultation.getDocuments();
        List<DocumentDetails> consultationDocuments = new ArrayList<>();
        for(Documents d : docs){
            Integer document_id = d.getId();
            String document_name = d.getName();
            String document_type=d.getDocument_type();
            boolean isAvailible=true;
            if (d.getData()==null){
                isAvailible=false;
            }
            DocumentDetails temp = new DocumentDetails(document_id, document_name,isAvailible,document_type);
            consultationDocuments.add(temp);
        }
        return consultationDocuments;
    }

    public void addDocument (Integer consultationId, Integer documentId)
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

    public void addPrescription (Integer consultationid, Integer documentid)
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

    public void removeDocument (Integer consultationid, Integer documentid)
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

    public List<PrevConsultations> getPrevConsultations (Integer patientId)
    {
        Patient p = patientRepository.findPatientById(patientId);
        String patient_name = p.getFname() + " " + p.getLname();
        List<Consultation> allConsultations = consultationRepository.getAllConsultationsByPid(patientId);
        List<PrevConsultations> all_consults = new ArrayList<>();
        for(Consultation consult: allConsultations ) {
            Timestamp start = consult.getStart_time();
            Timestamp end = consult.getEnd_time();
            Integer doctor_id = consult.getDoctor_id();
            Doctor doc = doctorRepository.findDocForConsultation(doctor_id);
            System.out.println("Doctor check"+doctor_id);
            String doc_name = doc.getFname() + " " + doc.getLname();
            Integer consult_id = consult.getId();
            Date followUp = consultationRepository.getFollowUpDate(consult_id);
            String specialization = doc.getSpecialization();
            List<DocumentDetails> documentDetailsList = getAllDocumentDetails(consult_id);
            Integer pid= consult.getPrescription_id();
            PrevConsultations individual_consultation = new PrevConsultations(start, end, doc_name, patient_name, doctor_id, consult_id, specialization,documentDetailsList,pid,followUp);
            all_consults.add(individual_consultation);
        }
        Collections.reverse(all_consults);
        return all_consults;
    }

    public List<PrevConsultations> getPrevConsultationsDoctor (Integer doctor_Id)
    {
        Doctor d = doctorRepository.findDocById(doctor_Id);
        String doc_name=d.getFname()+" "+d.getLname();
        String specialization = d.getSpecialization();
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
            PrevConsultations individual_consultation = new PrevConsultations(start, end, doc_name, patient_name, doctor_Id, consult_id, specialization,documentDetailsList,pid,followUp);
            all_consults.add(individual_consultation);
        }
        return all_consults;
    }

    public List<Map<String,Long>> getPrevConsultationsStats (Integer doctor_Id) throws Exception
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
                Integer val=stats.get(millis).intValue();
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

    public Integer addConsultation(Consultation consultation)
    {
        Consultation c = consultationRepository.save(consultation);
        Integer followupId = c.getFollowup_id();
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
        Consultation consultaion = c;

        queue.setConsultation(consultaion);
        dQueueRepository.save(queue);
        return c.getId();
    }

    public void updateConsultationEndtime(Consultation consultation)
    {
        Integer id = consultation.getId();
        Timestamp endTime = consultation.getEnd_time();
        consultationRepository.updateConsultationEndTime(id, endTime);
    }

    public void setFollowUpDate (Integer consultation_id, Date followUpDate){
        Consultation c = consultationRepository.findConsultationById(consultation_id);
        c.setFollow_up_date(followUpDate);
        consultationRepository.save(c);
    }

    public List<FollowUp> getFollowUp (Integer patientId) {
        List<PrevConsultations> consultationsList = getPrevConsultations(patientId);
        List<FollowUp> followUpList = new ArrayList<>();
        for(PrevConsultations c : consultationsList) {
            if(c.getFollowUpDate()==null){
                continue;
            }
            Date followUpDate = c.getFollowUpDate();
            Timestamp currentDate = c.getStartTime();
            java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
            if(currentDate.before(followUpDate))
            {
                Integer consultationId = c.getConsultId();
                Consultation consultation = consultationRepository.findConsultationById(consultationId);
                Integer doctorId = consultation.getDoctor_id();
                Doctor d = doctorRepository.findDocById(doctorId);

                Date newDate = new Date(followUpDate.getTime() + 6 * 3600*1000);

                String firstName = d.getFname();
                String lastName = d.getLname();
                FollowUp followUp = new FollowUp(consultationId, newDate, doctorId, firstName, lastName);
                followUpList.add(followUp);
            }
        }
        return followUpList;
    }
}
