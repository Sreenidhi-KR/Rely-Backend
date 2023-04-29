package com.example.backend.Controller;

import com.example.backend.Bean.Patient;
import com.example.backend.Service.UserService;
import com.example.backend.Utils.FileUploadUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("/uploadImage/{patient_id}")
    public void uploadImage(@PathVariable Integer patient_id,
                            @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = String.valueOf(patient_id) + ".jpg";
        Patient patient = userService.getPatient(patient_id);
        String uploadDir = "patient-photos/" + patient.getId();
        patient.setPhoto_url(uploadDir+"/"+patient.getId()+".jpg");
        userService.uploadDoc(patient);
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    }

    @RequestMapping(value = "/downloadImage/{patient_id}", method = RequestMethod.GET)
    public String fileUpload(@PathVariable Integer patient_id) throws SQLException, IOException {
        Patient patient = userService.getPatient(patient_id);
        String path = patient.getPhoto_url();
        File ImgPath = new File(path);
        FileInputStream fileInputStreamReader = new FileInputStream(ImgPath);
        byte[] bytes = new byte[(int) ImgPath.length()];
        fileInputStreamReader.read(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    @RequestMapping(value="/addPatient/{userId}", method=RequestMethod.POST)
    public void addPatient(@PathVariable Integer userId,@RequestBody Patient patient){
        patient.setActive(true);
        userService.addPatient(patient,userId);
    }

    @RequestMapping(value="/editPatient/{patientId}",method=RequestMethod.POST)
    public void editPatient(@PathVariable Integer patientId,@RequestBody Patient patient){
        String fname=patient.getFname();
        String lname=patient.getLname();
        String DOB=patient.getDOB();
        String blood_group=patient.getBlood_group();
        char sex=patient.getSex();
        String city=patient.getCity();
        String state=patient.getState();
        String pinCode=patient.getPinCode();
        String photo_url=patient.getPhoto_url();
        String relationship=patient.getRelationship();
        userService.editPatient(patientId,fname,lname,DOB,sex,blood_group,city,state,pinCode,photo_url,relationship);
    }

    @RequestMapping(value="/getPatients/{userId}",method=RequestMethod.GET)
    public List<Patient> getPatients(@PathVariable Integer userId){
        List<Patient> patients=userService.getAllPatients(userId);
        return patients;
    }

    @RequestMapping(value="/deletePatient/{patientId}",method=RequestMethod.DELETE)
    public void removePatient(@PathVariable Integer patientId){
        userService.removePatient(patientId);
    }
}
