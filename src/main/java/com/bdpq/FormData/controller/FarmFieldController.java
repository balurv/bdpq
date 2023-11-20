package com.bdpq.FormData.controller;

import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.repository.FarmFieldRepository;
import com.bdpq.FormData.service.FarmFieldService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/farmfield")
public class FarmFieldController {
    @Autowired
    private FarmFieldService farmFieldService;
    @PostMapping
    public ResponseEntity<FarmField> saveFarmField(@RequestBody FarmField farmField){
        FarmField saveFarmField = farmFieldService.saveFarm(farmField);
        return new ResponseEntity<>(saveFarmField, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getFarmField(@RequestParam Map<String, String> params){
        Optional<FarmField> dbfarmField = farmFieldService.getFarmField(params);
        if(dbfarmField.isPresent()){
            FarmField farmField = dbfarmField.get();
            return new ResponseEntity<>(farmField, HttpStatus.OK);
        }
        return new ResponseEntity<>("Provided data not found!", HttpStatus.NOT_FOUND);
    }
    @PutMapping
    public ResponseEntity<?>updateFarmField(@RequestBody FarmField farmField){
        FarmField farmField1 = farmFieldService.updateFarmField(farmField);
        if(farmField1 != null){
            return new ResponseEntity<>(farmField1, HttpStatus.OK);
        }
        return new ResponseEntity<>("Provided data not found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFarmField(@RequestParam Integer id){
        FarmField farmField = farmFieldService.deleteFarmField(id);
        if(farmField != null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
