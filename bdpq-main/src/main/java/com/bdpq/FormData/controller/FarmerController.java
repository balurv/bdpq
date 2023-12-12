package com.bdpq.FormData.controller;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.mapper.PersonMapper;
import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.model.Farmer;
import com.bdpq.FormData.repository.FarmerRepository;
import com.bdpq.FormData.service.FarmFieldService;
import com.bdpq.FormData.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/person")
public class FarmerController {
    @Autowired
    private FarmerService personService;
    @Autowired
    private FarmFieldService farmFieldService;

    @Autowired
    private FarmerRepository farmerRepository;
    @PostMapping
    public ResponseEntity<Farmer> savePerson(@RequestBody PersonDto persondto) {
        Farmer person = PersonMapper.toPerson(persondto);
        Farmer savedPerson = personService.saveFarmer(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getPerson(@RequestParam Map<String, String> params) {
        Optional<Farmer> person = personService.getFarmer(params);
        if (person.isPresent()) {
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("For Given filter, Data Not Found!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/farmfield")
    public ResponseEntity<?> getPersonFarmField(@RequestParam Long personId){
        Optional<Farmer> dbperson = personService.getFarmerById(personId);
        if(dbperson.isPresent()){
            Farmer person = dbperson.get();
            return new ResponseEntity<>(person.getFarmFields(), HttpStatus.OK);
        }
        return new ResponseEntity<>("For Given filter, Data not Found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updatePerson(@RequestBody PersonDto personDto) {
        Farmer person = personService.updateFarmer(personDto);
        if (person != null) {
            PersonDto updatedPersonDto = PersonMapper.toPersonDto(person);
            return new ResponseEntity<>(updatedPersonDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(new String("For given Data, Person Not Found!"), HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/{farmerId}/farmfield")
    public ResponseEntity<?> addFarmField(@PathVariable Long farmerId, @RequestBody FarmField farmField){
        Farmer farmer = personService.updateFarmerFarmField(farmerId, farmField);
        if(farmer != null){
            return new ResponseEntity(farmer, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(@RequestParam Integer id) {
        Farmer person = personService.deleteFarmer(id);
        if (person != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
