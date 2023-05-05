package com.example.backend.Controller;

import com.example.backend.Bean.*;
import com.example.backend.Payload.Request.*;
import com.example.backend.Payload.Response.JwtResponse;
import com.example.backend.Payload.Response.MessageResponse;
import com.example.backend.Payload.Response.TokenRefreshResponse;
import com.example.backend.Repository.AdminRepository;
import com.example.backend.Repository.DoctorRepository;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Security.Exception.TokenRefreshException;
import com.example.backend.Security.Jwt.JwtUtils;
import com.example.backend.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BlacklistService blacklistService;

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

    @Autowired
    RefreshTokenService refreshTokenService;

    @Resource(name = "DQueueService")
    public DQueueService dQueueService;

    @Resource(name ="userService")
    public UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUp userSignUp) {
        if (userRepository.existsByUserName(userSignUp.getUsername()) || doctorRepository.existsByUserName(userSignUp.getUsername()) || adminRepository.existsByUserName(userSignUp.getUsername())) {
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

    @PostMapping("/user/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserLogin userLogin) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                refreshToken.getToken()));
    }

    @PostMapping("/user/refreshtoken")
    public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUserName());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/user/signout/{user_id}")
    @PreAuthorize("hasRole('USER')")
    public void signout(@RequestHeader(value="Authorization") String token, @PathVariable Integer user_id) {
            Blacklist blacklist=new Blacklist(token);
            blacklistService.setToken(blacklist);
            blacklistService.deleteRefreshTokenUser(user_id);
    }

    @PostMapping("/doctor/signup")
    public ResponseEntity<?> registerUser(@RequestBody DoctorSignUp signUp) {

        if (doctorRepository.existsByUserName(signUp.getUsername()) || userRepository.existsByUserName(signUp.getUsername()) || adminRepository.existsByUserName(signUp.getUsername())) {
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
                signUp.getAvailable_timings(),
                signUp.getCity(),
                signUp.getState(),
                signUp.getClinic_address(),
                signUp.getPhoto_url(),
                signUp.isOnline_status(),
                signUp.getUsername(),
                encoder.encode(signUp.getPassword()),
                signUp.getEmail(),
                signUp.getToken(),
                signUp.getPhoneNo()
        );

        // Create new Doctor's account
        Set<Role> roles = new HashSet<>();
        Role doctorRole = roleRepository.findByName(ERole.ROLE_DOCTOR)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        System.out.println((doctorRole));
        roles.add(doctorRole);

        doctor.setRoles(roles);
        doctorRepository.save(doctor);
        dQueueService.createDQueueForDoctorId(doctor.getId());
        return ResponseEntity.ok(new MessageResponse("Doctor registered successfully!"));
    }

    @PostMapping("/doctor/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody DoctorLogin loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println(jwt);

        DoctorDetailsImpl doctorDetails = (DoctorDetailsImpl) authentication.getPrincipal();
        List<String> roles = doctorDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshTokenDoctor refreshToken = refreshTokenService.createRefreshTokenDoctor(doctorDetails.getId());


        return ResponseEntity.ok(new JwtResponse(jwt,
                doctorDetails.getId(),
                doctorDetails.getUsername(),
                doctorDetails.getEmail(),
                roles,
                refreshToken.getToken()));
    }

    @PostMapping("/doctor/refreshtoken")
    public ResponseEntity<?> refreshtokenDoctor(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findDoctorByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpirationDoctor)
                .map(RefreshTokenDoctor::getDoctor)
                .map(doctor -> {
                    String token = jwtUtils.generateTokenFromUsername(doctor.getUserName());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/doctor/signout/{doctor_id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public void doctorSignout(@RequestHeader(value="Authorization") String token, @PathVariable Integer doctor_id) {
        Blacklist blacklist=new Blacklist(token);
        blacklistService.setToken(blacklist);
        blacklistService.deleteRefreshTokenDoctor(doctor_id);
    }

    @PostMapping("/admin/signup")
    public ResponseEntity<?> registerUser(@RequestBody AdminSignUp signUp) {

        if (adminRepository.existsByUserName(signUp.getUsername()) || doctorRepository.existsByUserName(signUp.getUsername()) || userRepository.existsByUserName(signUp.getUsername())) {
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
        System.out.println(jwt);

        AdminDetailsImpl adminDetails = (AdminDetailsImpl) authentication.getPrincipal();
        List<String> roles = adminDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(adminDetails.getId());


        return ResponseEntity.ok(new JwtResponse(jwt,
                adminDetails.getId(),
                adminDetails.getUsername(),
                roles,
                refreshToken.getToken()));
    }

}