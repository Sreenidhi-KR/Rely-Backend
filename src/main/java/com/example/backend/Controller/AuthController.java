package com.example.backend.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.backend.Bean.*;
import com.example.backend.Payload.Request.*;
import com.example.backend.Repository.AdminRepository;
import com.example.backend.Repository.DoctorRepository;
import com.example.backend.Service.AdminDetailsImpl;
import com.example.backend.Service.DoctorDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.Payload.Response.JwtResponse;
import com.example.backend.Payload.Response.MessageResponse;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Security.Jwt.JwtUtils;
import com.example.backend.Service.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLogin userLogin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUp userSignUp) {
        if (userRepository.existsByUserName(userSignUp.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(userSignUp.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(userSignUp.getUsername(),
                userSignUp.getEmail(),
                encoder.encode(userSignUp.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/doctor/signup")
    public ResponseEntity<?> registerUser(@RequestBody DoctorSignUp signUp) {

        if (doctorRepository.existsByUserName(signUp.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (doctorRepository.existsByEmail(signUp.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Doctor doctor = new Doctor(signUp.getFname(),
                signUp.getLname(),
                signUp.getDOB(),
                signUp.getSex(),
                signUp.getChannel_name(),
                signUp.getSpecialization(),
                signUp.getQualification(),
                signUp.getDescription(),
                signUp.getRating(),
                signUp.getAvailable_timings(),
                signUp.getCity(),
                signUp.getState(),
                signUp.getClinic_address(),
                signUp.getPhoto_url(),
                signUp.isOnline_status(),
                signUp.getUsername(),
                encoder.encode(signUp.getPassword()),
                signUp.getEmail(),
                signUp.getToken()
        );

        // Create new Doctor's account
        Set<Role> roles = new HashSet<>();
        Role doctorRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        System.out.println((doctorRole));
        roles.add(doctorRole);

        doctor.setRoles(roles);
        doctorRepository.save(doctor);
        return ResponseEntity.ok(new MessageResponse("Doctor registered successfully!"));
    }

    @PostMapping("/doctor/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody DoctorLogin loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        DoctorDetailsImpl doctorDetails = (DoctorDetailsImpl) authentication.getPrincipal();
        List<String> roles = doctorDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                doctorDetails.getId(),
                doctorDetails.getUsername(),
                doctorDetails.getEmail(),
                roles));
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<?> registerUser(@RequestBody AdminSignUp signUp) {

        if (adminRepository.existsByUserName(signUp.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Admin admin = new Admin(
                signUp.getUsername(),
                encoder.encode(signUp.getPassword())
        );

        // Create new Admin's account
        Set<String> strRoles = signUp.getRole();
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        admin.setRoles(roles);
        adminRepository.save(admin);
        return ResponseEntity.ok(new MessageResponse("Admin registered successfully!"));
    }

    @PostMapping("/admin/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody AdminLogin loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AdminDetailsImpl adminDetails = (AdminDetailsImpl) authentication.getPrincipal();
        List<String> roles = adminDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                adminDetails.getId(),
                adminDetails.getUsername(),
                roles));
    }

}