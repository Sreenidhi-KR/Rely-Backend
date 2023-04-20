package com.example.backend.Service;

import com.example.backend.Bean.Consultation;
import com.example.backend.Bean.DQueue;
import com.example.backend.Bean.Doctor;
import com.example.backend.Bean.Patient;
import com.example.backend.Payload.Response.DQueueInfo;
import com.example.backend.Repository.DQueueRepository;
import com.example.backend.Repository.DoctorRepository;
import com.example.backend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class DQueueService {

    @Autowired
    private DQueueRepository dQueueRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public void createDQueue(DQueue queue){ dQueueRepository.save(queue);}

    public void createDQueueForDoctorId(int doctorId){
        Doctor find_doctor = doctorRepository.findDocById(doctorId);
        DQueue dQueue = new DQueue(find_doctor);
        dQueueRepository.save(dQueue);
    }


    public List<Patient> getAllPatientsFromDQueue(int doctorId) {
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        return queue.getPatientList();
    }

    public void addPatientToQueue(Integer doctorId, Integer patientId) {
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        Patient patient = patientRepository.findPatientById(patientId);
        if (queue == null) {
            throw new IllegalArgumentException("No queue found for doctor");
        }
        List<Patient> patientList = queue.getPatientList();
        if(patientList == null){
            patientList = new ArrayList<>();
        }
        if(!patientList.contains(patient) && doctor.getLimit()>patientList.size()) {
            patientList.add(patient);
        }
        else if(doctor.getLimit()<=patientList.size()){
            System.out.println("Queue Limit Reached");
        }
        dQueueRepository.save(queue);
    }
    public void removePatientFromQueue(Integer doctorId, Integer patientId) {
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        Patient patient = patientRepository.findPatientById(patientId);
        if (queue == null) {
            throw new IllegalArgumentException("No queue found for doctor");
        }
        List<Patient> patientList = queue.getPatientList();
        if(patientList.contains(patient)) {
            patientList.remove(patient);
        }
        dQueueRepository.save(queue);
    }

    public DQueueInfo getPatientIndexFromQueue(Integer doctorId, Integer patientId){
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        Patient patient = patientRepository.findPatientById(patientId);
        if (queue == null) {
            throw new IllegalArgumentException("No queue found for doctor");
        }
        List<Patient> patientList = queue.getPatientList();
//        Collections.reverse(patientList);
        DQueueInfo dQueueInfo = new DQueueInfo();
        if(!patientList.contains(patient)){
            dQueueInfo.setIndex(-1);
            dQueueInfo.setAccept(false);
            return dQueueInfo;
        }

        dQueueInfo.setIndex(patientList.indexOf(patient)+1);
        dQueueInfo.setAccept(queue.isAccept());
        return dQueueInfo;
    }

    public Integer getDqueueId(Integer doctorId){
        Doctor doctor = doctorRepository.findDocById(doctorId);
        DQueue queue = dQueueRepository.findDQueueByDoctor(doctor);
        Integer qid=queue.getId();
        return qid;
    }

    public  Integer getConsultationId(int Qid)
    {
        DQueue queue=dQueueRepository.findDQueueById(Qid);
        if(queue.getConsultationList().size()==0){
            return -1;
        }
        Consultation consultation=queue.getConsultationList().get(0);
        System.out.println(consultation.getId());
        return consultation.getId();
    }

    public Integer getPatientId(int Qid)
    {
        DQueue queue=dQueueRepository.findDQueueById(Qid);
        Consultation consultation=queue.getConsultationList().get(0);
        return consultation.getPatient_id();
    }

    public Doctor getQuickDoctor()
    {
        List<DQueue> dQueueList = dQueueRepository.findAll();
        int minLength = Integer.MAX_VALUE;
        Doctor quickDoctor = null;
        for(DQueue dQ: dQueueList)
        {
            List<Patient> patientList = dQ.getPatientList();
            boolean onlineStatus = dQ.getDoctor().isOnline_status();
            int queueLimit = dQ.getDoctor().getLimit();

            if((patientList.size()+1)>queueLimit){
                continue;
            }
            if(patientList.isEmpty() && onlineStatus){
               quickDoctor = dQ.getDoctor();
               break;
            }
            if(onlineStatus)
            {
                int dqueueLength = patientList.size();
                if (dqueueLength < minLength) {
                    minLength = dqueueLength;
                    quickDoctor = dQ.getDoctor();
                }
            }
        }
        return  quickDoctor;
    }

    public void removeAllPatientsFromQueue(Integer qid){
        DQueue queue = dQueueRepository.findDQueueById(qid);
        queue.setPatientList(null);
        dQueueRepository.save(queue);
    }

    public boolean toggleAcceptPatient(Integer qid, boolean bool){
        DQueue queue = dQueueRepository.findDQueueById(qid);
        queue.setAccept(bool);
        dQueueRepository.save(queue);
        return  queue.isAccept();
    }
}
