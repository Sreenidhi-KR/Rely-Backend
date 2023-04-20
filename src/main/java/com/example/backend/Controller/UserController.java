package com.example.backend.Controller;

import com.example.backend.Bean.Patient;
import com.example.backend.Service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value="/addPatient/{userId}", method=RequestMethod.POST)
    public void addPatient(@PathVariable int userId,@RequestBody Patient patient){
        userService.addPatient(patient,userId);
    }

    @RequestMapping(value="/editPatient/{patientId}",method=RequestMethod.POST)
    public void editPatient(@PathVariable int patientId,@RequestBody Patient patient){
        String fname=patient.getFname();
        String lname=patient.getLname();
        String DOB=patient.getDOB();
        String blood_group=patient.getBlood_group();
        char sex=patient.getSex();
        String city=patient.getCity();
        String state=patient.getState();
        String abdm_no=patient.getAbdm_no();
        String photo_url="No photo";
        String relationship=patient.getRelationship();
        userService.editPatient(patientId,fname,lname,DOB,sex,blood_group,city,state,abdm_no,photo_url,relationship);
    }

//    public void editPatient(int patientId, String fname, String lname, String DOB,char sex, String blood_group,String city,String state,String abdm_no,String photo_url,String relationship){
//        patientRepository.updatePatient(patientId,fname,lname,DOB,sex,blood_group,city,state,abdm_no,relationship);
//    }

    @RequestMapping(value="/getPatients/{userId}",method=RequestMethod.GET)
    public List<Patient> getPatients(@PathVariable int userId){
        List<Patient> patients=userService.getAllPatients(userId);
        return patients;
    }

    @RequestMapping(value="/deletePatient/{patientId}",method=RequestMethod.DELETE)
    public void removePatient(@PathVariable int patientId){
        userService.removePatient(patientId);
    }
}
