package com.example.backend.Controller;

import com.example.backend.Bean.Doctor;
import com.example.backend.Service.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/doctor")
public class DoctorController extends Exception {
    @Resource(name = "doctorService")
    private DoctorService doctorService;

    @RequestMapping(value = "/getAllDoctors", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<Doctor> getAllDoctors() {
        try {
            List<Doctor> Doctors = doctorService.list();
            return Doctors;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @RequestMapping(value = "/getAllDoctorsBySpec/{specialization}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public List<Doctor> getAllDoctorsBySpec(@PathVariable String specialization) {
        List<Doctor> Doctors = doctorService.listBySpec(specialization);
        return Doctors;
    }

    @RequestMapping(value = "/deleteDoctor/{doctor_id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void deleteDoctor(@PathVariable Integer doctor_id) {
        System.out.println(doctor_id);
        doctorService.deleteDoctorById(doctor_id);
    }

    @RequestMapping(value = "/updateDoctor", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void updateDoctor(@RequestBody Doctor doctor) {
        doctorService.updateDoctor(doctor);
    }

    @RequestMapping(value = "/updateDoctorCall", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void updateDoctorCall(@RequestBody Doctor doctor) {doctorService.updateDoctorCall(doctor);}

    @RequestMapping(value = "/getDoctorById/{doctor_id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public Doctor getDoctorById(@PathVariable Integer doctor_id) {
        Doctor doctor = doctorService.findById(doctor_id);
        return doctor;
    }

    @RequestMapping(value = "/getPhotoById/{doctor_id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public String fileUpload(@PathVariable Integer doctor_id) throws SQLException, IOException {
        Doctor doctor = doctorService.findById(doctor_id);
        String path = doctor.getPhoto_url();
        File ImgPath = new File(path);
        FileInputStream fileInputStreamReader = new FileInputStream(ImgPath);
        byte[] bytes = new byte[(int)ImgPath.length()];
        fileInputStreamReader.read(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    @RequestMapping(value = "/setQueueLimit/{doctor_id}/{limit}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public void setQueueLimit(@PathVariable Integer doctor_id,@PathVariable Integer limit) throws SQLException, IOException {
        Doctor doctor = doctorService.findById(doctor_id);
        doctor.setLimit(limit);
        System.out.println("Limit:"+limit);
        updateDoctor(doctor);
    }

    @RequestMapping(value = "/updateDoctorRating/{doctor_id}/{rating}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void updateDoctorRating(@PathVariable Integer doctor_id, @PathVariable Integer rating) {
        doctorService.updateDoctorRating(doctor_id, rating);
    }

    @RequestMapping(value = "/getOnlineStatus/{doctor_id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Boolean getOnlineStatus(@PathVariable Integer doctor_id) {
        return doctorService.getOnlineStatus(doctor_id);
    }
}