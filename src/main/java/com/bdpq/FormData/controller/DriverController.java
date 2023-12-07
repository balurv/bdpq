package com.bdpq.FormData.controller;


import com.bdpq.FormData.model.Driver;
import com.bdpq.FormData.model.DriverLicense;
import com.bdpq.FormData.model.Machinery;
import com.bdpq.FormData.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;
    @PostMapping
    public ResponseEntity<Driver> saveDriver(@RequestBody Driver dirver) {
        Driver savedPerson = driverService.saveDriver(dirver);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getDriver(@RequestParam Map<String, String> params) {
        Optional<Driver> person = driverService.getDriver(params);
        if (person.isPresent()) {
            Driver driver = person.get();
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>("For Given filter, Data Not Found!", HttpStatus.NOT_FOUND);
    }

//    @GetMapping("/farmfield")
//    public ResponseEntity<?> getPersonFarmField(@RequestParam Long personId){
//        Map<String, String> param = new HashMap<>();
//        param.put("id", personId.toString());
//        Optional<Farmer> dbperson = personService.getFarmer(param);
//        if(dbperson.isPresent()){
//            Farmer person = dbperson.get();
//            return new ResponseEntity<>(person.getFarmFields(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>("For Given filter, Data not Found!", HttpStatus.NOT_FOUND);
//    }

    @PutMapping
    public ResponseEntity<?> updateDriver(@RequestBody Driver personDto) {
        Driver person = driverService.updateDriver(personDto);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>(new String("For given Data, Person Not Found!"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePerson(@RequestParam Integer id) {
        Driver person = driverService.deleteDriver(id);
        if (person != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PatchMapping("/{driverId}/driverlicense")
    public ResponseEntity<?> addDriverLicense(@PathVariable Long driverId, @RequestBody DriverLicense driverLicense){
        Driver driver = driverService.updateDriverLicense(driverId, driverLicense);
        if(driver != null){
            return new ResponseEntity<>(driver, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    @PatchMapping("/{driverId}/machine")
    public  ResponseEntity<?> addMachiney(@PathVariable Long driverId, @RequestBody Machinery machinery){
        Driver driver = driverService.updateMachinery(driverId, machinery);
        if(driver != null){
            return new ResponseEntity<>(driver, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
}
