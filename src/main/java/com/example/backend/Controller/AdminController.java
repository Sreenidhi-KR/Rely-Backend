package com.example.backend.Controller;

import com.example.backend.Bean.Admin;
import com.example.backend.Bean.Doctor;
import com.example.backend.Service.AdminService;
import com.example.backend.Service.DoctorService;
import com.example.backend.Utils.FileUploadUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Resource(name = "adminService")
    private AdminService adminService;
    @Resource(name = "doctorService")
    private DoctorService doctorService;

    @PostMapping("/uploadImage/{doctor_id}")
    @PreAuthorize("hasRole('USER') or hasRole('DOCTOR') or hasRole('ADMIN')")
    public void uploadImage(@PathVariable int doctor_id,
    @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = String.valueOf(doctor_id) + ".jpg";
        Doctor doctor = doctorService.findById(doctor_id);
        String uploadDir = "user-photos/" + doctor.getId();
        doctor.setPhoto_url(uploadDir+"/"+doctor.getId()+".jpg");
        doctorService.updateDoctor(doctor);
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
    }
}
