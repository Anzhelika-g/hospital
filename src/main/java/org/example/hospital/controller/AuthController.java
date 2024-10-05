package org.example.hospital.controller;

import jakarta.servlet.Registration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.hospital.request.DoctorUserRequest;
import org.example.hospital.request.PatientUserRequest;
import org.example.hospital.service.DoctorService;
import org.example.hospital.service.PatientService;
import org.example.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.example.hospital.model.UserModel;
import org.example.hospital.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Public login endpoints
    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login (@RequestBody UserModel userModel, HttpServletRequest request) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            // Create a new session and add the security context.
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Public patient registration endpoint
    @PostMapping("/patient/register")
    public ResponseEntity<HttpStatus> registerPatient(@RequestBody PatientUserRequest patientUserRequest){
        patientUserRequest.getUserDTO().setRole(Role.PATIENT.name());
        //Register both the patient and the user
        patientService.addPatient(patientUserRequest.getPatientDTO(), patientUserRequest.getUserDTO());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Role-based logout endpoint (for PATIENT, DOCTOR, ADMIN, LAB_ASSISTANT)
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate(); //Invalidate session to log out
            SecurityContextHolder.clearContext(); //Clear security context
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //No active session found
    }

    //Get user profile (must be logged in)
    @GetMapping("/profile")
    public ResponseEntity<HttpStatus> getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.OK); //User is authenticated
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //User not authenticated
    }

    //TODO:
//    // Secured Admin Registration Endpoint (only accessible by admins)
//    @Secured("ROLE_ADMIN") //Spring Security annotation to restrict access to admins
//    @PostMapping("/admin/register")
//    public ResponseEntity<HttpStatus> registerAdmin(@RequestBody UserModel userModel){
//        userService.saveUser(userModel.getEmail(), passwordEncoder.encode(userModel.getPassword()), Role.ADMIN);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    // Secured Doctor Registration Endpoint (only accessible by admins)
    @Secured("ROLE_ADMIN")
    @PostMapping("/doctor/register")
    public ResponseEntity<HttpStatus> registerDoctor(@RequestBody DoctorUserRequest doctorUserRequest){
        doctorUserRequest.getUserDTO().setRole(Role.DOCTOR.name());
        //Register both the doctor and the user
        doctorService.addDoctor(doctorUserRequest.getDoctorDTO(), doctorUserRequest.getUserDTO());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //TODO: clarify;

    // Secured Lab Assistant Registration Endpoint (only accessible by admins)
//    @Secured("ROLE_ADMIN")
//    @PostMapping("/labassistant/register")
//    public ResponseEntity<HttpStatus> registerLabAssistant(@RequestBody UserModel userModel){
//        labAssistantUserRequest.getUserDTO().setRole(Role.ADMIN.name());
//        userService.saveUser(userModel.getEmail(), passwordEncoder.encode(userModel.getPassword()), Role.LAB_ASSISTANT);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }


}
