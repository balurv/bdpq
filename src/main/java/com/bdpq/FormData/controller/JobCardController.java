package com.bdpq.FormData.controller;

import com.bdpq.FormData.dto.FarmerJobCard;
import com.bdpq.FormData.model.JobCard;
import com.bdpq.FormData.model.JobStatus;
import com.bdpq.FormData.service.JobCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/jobcard")
public class JobCardController {
    @Autowired
    private JobCardService jobCardService;
    @PostMapping
    public ResponseEntity<?> createJobCard(@RequestBody FarmerJobCard farmerJobCard){
        JobCard jobCard= jobCardService.create(farmerJobCard);
        return new ResponseEntity<>(jobCard, HttpStatus.CREATED);
    }
    @GetMapping("/status")
    public ResponseEntity<?>getJobCardByStatus(@RequestParam JobStatus jobStatus, @RequestParam Long farmerId){
        List<JobCard> jobCardList = jobCardService.getJobCardByStatus(jobStatus, farmerId);
        if(!jobCardList.isEmpty()){
            return new ResponseEntity<>(jobCardList, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<?> getJobCardByFarmerId(@PathVariable Long farmerId){
        List<JobCard> jobCardList = jobCardService.getJobCard(farmerId);
        return new ResponseEntity<>(jobCardList, HttpStatus.OK);
    }
}
