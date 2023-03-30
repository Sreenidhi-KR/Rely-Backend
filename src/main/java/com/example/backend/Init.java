//package com.example.backend;
//
//import com.example.backend.Bean.*;
//import com.example.backend.Repository.*;
//import com.example.backend.Service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//
//@Component
//public class Init implements CommandLineRunner {
//
//    @Autowired
//    DoctorRepository doctorRepository;
//
//    @Autowired
//    PatientRepository patientRepository;
//
//    @Autowired
//    DQueueRepository dQueueRepository;
//
//    @Autowired
//    ConsultationRepository consultationRepository;
//
//    @Autowired
//    DocumentsRepository documentsRepository;
//
//    @Resource(name = "doctorService")
//    private DoctorService doctorService;
//
//    @Resource(name = "DQueueService")
//    private DQueueService dQueueService;
//
//    @Resource(name = "consultationService")
//    private ConsultationService consultationService;
//
//    @Resource(name = "documentsServiceImpl")
//    private DocumentsServiceImpl documentServiceImpl;
//
//    @Override
//    @Transactional
//    public void run(String... arg) throws IOException {
//
//        try {
//            System.out.println("Create Doctor 1");
//            Doctor doctor1 = new Doctor("Preyas" , "gandu");
//            doctorRepository.save(doctor1);
//
//            System.out.println("Create Doctor 2");
//            Doctor doctor2 = new Doctor("AKB" , "loper");
//            doctorRepository.save(doctor2);
//
//
//            System.out.println("Create Doctor Queue");
////            System.out.println("Find Doctor by ID");
////            Doctor find_doctor = doctorRepository.findDocById(1);
////            System.out.println("Create Doctor queue");
////            DQueue q = new DQueue(find_doctor);
////            System.out.println("Save DQueue");
////            dQueueRepository.save(q);
//
//            dQueueService.createDQueueForDoctorId(1);
//
//            System.out.println("Create Patient 1");
//            Patient patient = new Patient("Kiran");
//            System.out.println("Save patient 1");
//            patientRepository.save(patient);
//
//            System.out.println("Create Patient 2");
//            Patient patient2 = new Patient("Aditya");
//            System.out.println("Save patient 2");
//            patientRepository.save(patient2);
//
//            System.out.println("Add Patient1 to Quueue");
//            dQueueService.addPatientToQueue(1,1);
//
//            System.out.println("Add Patient2 to Quueue");
//            dQueueService.addPatientToQueue(1,2);
//
//            System.out.println("get patients from queue of doctor 1");
//            List<Patient> patientList = dQueueService.getAllPatientsFromDQueue(1);
//
//            for (Patient p: patientList
//            ) {
//                System.out.println(p.getFname());
//            }
//
//            System.out.println("position of patient 1 " + dQueueService.getPatientIndexFromQueue(doctor1.getId(),patient.getId()));
//            System.out.println("position of patient 2 " + dQueueService.getPatientIndexFromQueue(doctor1.getId(),patient2.getId()));
//            System.out.println("Delete patient 1  from queue");
//            dQueueService.removePatientFromQueue(1,1);
//
//            System.out.println("get patients AFTER DELETE from queue of doctor 1");
//            patientList = dQueueService.getAllPatientsFromDQueue(1);
//
//            for (Patient p: patientList
//            ) {
//                System.out.println(p.getFname());
//            }
//
//            System.out.println("Create Consultation");
//            Consultation cons = new Consultation(1, 1);
//            consultationRepository.save(cons);
//
//            System.out.println("Create Documents");
//            Documents docu = new Documents("abc");
//            documentsRepository.save(docu);
//
//            System.out.println("Consultation Id");
//            System.out.println(cons.getId());
//
//            System.out.println("add_documents");
//            consultationService.addDocument(3,4);
//
//
//            System.out.println("get documents for a given consultationId");
//            List<DocumentDetails> docs = consultationService.getAllDocuments(3);
//
//            for (DocumentDetails d: docs
//            ) {
//                System.out.println(d.getName());
//            }
//
////            System.out.println("remove_documents");
////            consultationService.removeDocument(3,4);
//
//
//
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
//    }
//
//
//}
//
