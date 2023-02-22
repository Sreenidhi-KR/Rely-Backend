package com.example.backend.Controller;
import com.example.backend.Bean.Doctor;
import com.example.backend.Service.DoctorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
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

}