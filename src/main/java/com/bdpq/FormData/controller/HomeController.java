package com.bdpq.FormData.controller;

import com.bdpq.FormData.dto.LoginDto;
import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.mapper.PersonMapper;
import com.bdpq.FormData.model.Person;
import com.bdpq.FormData.model.Role;
import com.bdpq.FormData.repository.RoleRepository;
import com.bdpq.FormData.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PersonService personService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User login successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody PersonDto signUpDto) {
        System.out.println(signUpDto.toString());
        Map<String, String> params = new HashMap<>();
        params.put("email", signUpDto.getEmail().toString());
        params.put("phone", signUpDto.getPhone().toString());
        if (!personService.getPerson(params).isPresent()) {
            Person person = PersonMapper.toPerson(signUpDto);
            person.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            Role roles = roleRepository.findByName("ROLE_ADMIN").get();
            person.setRole(Collections.singleton(roles));
            personService.savePerson(person);
            return new ResponseEntity<>("User Registerd sucessfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email already exist!", HttpStatus.BAD_REQUEST);
        }
    }
}
