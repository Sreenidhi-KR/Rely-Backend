package com.example.backend.Controller;

import com.example.backend.Bean.Patient;
import com.example.backend.Service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @RequestMapping(value="/getPatients/{userId}",method=RequestMethod.GET)
    public List<Patient> getPatients(@PathVariable int userId){
        List<Patient> patients=userService.getAllPatients(userId);
        return patients;
    }
}
