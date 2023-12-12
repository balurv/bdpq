package com.bdpq.FormData.controller;

import com.bdpq.FormData.dto.LoginDto;
import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.mapper.PersonMapper;
import com.bdpq.FormData.model.Farmer;
import com.bdpq.FormData.model.Role;
import com.bdpq.FormData.repository.RoleRepository;
import com.bdpq.FormData.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")

public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FarmerService personService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, String> params = new HashMap<>();
        params.put("name", loginDto.getEmail());
        Optional<Farmer> farmer = personService.getFarmer(params);
        if(farmer.isPresent()){
            return new ResponseEntity<>(farmer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("User login successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody PersonDto signUpDto) {
        Map<String, String> params = new HashMap<>();
        params.put("email", signUpDto.getEmail().toString());
        params.put("phone", signUpDto.getPhone().toString());
        if (!personService.getFarmer(params).isPresent()) {
            Farmer person = PersonMapper.toPerson(signUpDto);
            person.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            Role roles = roleRepository.findByName("ROLE_ADMIN").get();
            person.setRole(Collections.singleton(roles));
            personService.saveFarmer(person);
            return new ResponseEntity<>("User Registerd sucessfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email already exist!", HttpStatus.BAD_REQUEST);
        }
    }
}
