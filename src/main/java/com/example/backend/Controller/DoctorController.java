package com.example.backend.Controller;
import com.example.backend.Bean.Doctor;
import com.example.backend.Service.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    @Resource(name = "doctorService")
    private DoctorService doctorService;

    @RequestMapping(value = "/getAllDoctors", method = RequestMethod.GET)
    public List<Doctor> getAllDoctors() {
        List<Doctor> Doctors = doctorService.list();
        return Doctors;
    }
    @RequestMapping(value = "/getAllDoctorsBySpec/{specialization}", method = RequestMethod.GET)
    public List<Doctor> getAllDoctorsBySpec(@PathVariable String specialization) {
        List<Doctor> Doctors = doctorService.listBySpec(specialization);
        return Doctors;
    }

    @RequestMapping(value = "/addDoctor", method = RequestMethod.POST)
    public void addDoctor(@RequestBody Doctor doctor) {
        doctorService.addDoctor(doctor);
    }

    @RequestMapping(value = "/deleteDoctor/{doctor_id}", method = RequestMethod.POST)
    public void deleteDoctor(@PathVariable int doctor_id) {
        System.out.println(doctor_id);
        doctorService.deleteDoctorById(doctor_id);
    }

    @RequestMapping(value = "/updateDoctor", method = RequestMethod.POST)
    public void updateDoctor(@RequestBody Doctor doctor) {
        doctorService.updateDoctor(doctor);
    }

    @RequestMapping(value = "/getDoctorById/{doctor_id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('DOCTOR')")
    public Doctor getDoctorById(@PathVariable int doctor_id) {
        Doctor doctor = doctorService.findById(doctor_id);
        return doctor;
    }


}