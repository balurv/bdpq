package com.bdpq.FormData.controller;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.mapper.PersonMapper;
import com.bdpq.FormData.model.Person;
import com.bdpq.FormData.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping
    public ResponseEntity<Person> savePerson(@RequestBody PersonDto persondto) {
        Person person = PersonMapper.toPerson(persondto);
        Person savedPerson = personService.savePerson(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getPerson(@RequestParam Map<String, String> params) {
        Optional<Person> person = personService.getPerson(params);
        if (person.isPresent()) {
            PersonDto personDto = PersonMapper.toPersonDto(person.get());
            return new ResponseEntity<>(personDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(new String("For Given filter, Data Not Found!"), HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updatePerson(@RequestBody PersonDto personDto) {
        Person person = personService.updatePerson(personDto);
        if (person != null) {
            PersonDto updatedPersonDto = PersonMapper.toPersonDto(person);
            return new ResponseEntity<>(updatedPersonDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(new String("For given Data, Person Not Found!"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(@RequestParam Integer id) {
        Person person = personService.deletePerson(id);
        if (person != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
