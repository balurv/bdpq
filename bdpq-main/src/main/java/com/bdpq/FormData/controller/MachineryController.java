package com.bdpq.FormData.controller;

import com.bdpq.FormData.model.Machinery;
import com.bdpq.FormData.service.MachineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/machinery")
public class MachineryController {
    @Autowired
    private MachineryService machineryService;
    @GetMapping
    public ResponseEntity<?> getMachine(@RequestParam Long id){
        Machinery machinery = machineryService.getById(id);
        if(machinery != null){
            return new ResponseEntity<>(machinery, HttpStatus.OK);
        }
        return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<Machinery>postMachine(@RequestBody Machinery machinery){
        Machinery savedMachinery = machineryService.save(machinery);
        return new ResponseEntity<>(savedMachinery, HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Machinery> putMachine(@RequestBody Machinery machinery){
        Machinery updatedMachinery = machineryService.update(machinery);
        return new ResponseEntity<>(updatedMachinery, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?>deleteMachine(@RequestParam Long id){
        Machinery machinery = machineryService.delete(id);
        if(machinery != null ){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
