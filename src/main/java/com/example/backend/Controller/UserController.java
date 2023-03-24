package com.example.backend.Controller;

import com.example.backend.Bean.Patient;
import com.example.backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource(name = "userService")
    private UserService userService;
//
//    @RequestMapping(value = "/addPatient", method = RequestMethod.POST)
//    public ResponseEntity addPatient(@RequestBody Patient patient) {
//        Boolean withinLimit = userService.addPatient(patient);
//        return null;
//    }
}
