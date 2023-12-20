package com.bdpq.FormData.controller;

import com.bdpq.FormData.dto.FarmerJobCardDto;
import com.bdpq.FormData.dto.JobCardFilterDto;
import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.model.JobCard;
import com.bdpq.FormData.model.JobStatus;
import com.bdpq.FormData.service.JobCardService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/jobcard")
public class JobCardController {
    @Autowired
    private JobCardService jobCardService;
    @PostMapping
    public ResponseEntity<?> createJobCard(@RequestBody FarmerJobCardDto farmerJobCard){
        JobCard jobCard= jobCardService.create(farmerJobCard);
        return new ResponseEntity<>(jobCard, HttpStatus.CREATED);
    }
    @GetMapping("/status")
    public ResponseEntity<?>getJobCardByStatus(@RequestParam JobStatus jobStatus, @RequestParam Long farmerId,@PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<JobCard> jobCardList = jobCardService.getJobCardByStatus(jobStatus, farmerId, pageable);
        if(!jobCardList.isEmpty()){
            return new ResponseEntity<>(jobCardList, HttpStatus.OK);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<?> getJobCardByFarmerId(@PathVariable Long farmerId, @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<JobCard> jobCardList = jobCardService.getJobCard(farmerId, pageable);
        return new ResponseEntity<>(jobCardList, HttpStatus.OK);
    }
    @GetMapping("/driver")
    public ResponseEntity<?> getJobCard(JobCardFilterDto jobCardFilterDto,@PageableDefault(page = 0, size = 10) Pageable pageable){
        System.out.println(jobCardFilterDto.toString());

        List<JobCard> jobCardList = jobCardService.getJobCard(jobCardFilterDto, pageable);
        return new ResponseEntity<>(jobCardList, HttpStatus.OK) ;
    }

    @PutMapping("/jobstatus")
    public ResponseEntity<?> updateJobstatus(@RequestParam Long jobCardId, @RequestParam JobStatus jobStatus, @RequestParam Long driverId){
        JobCard jobCard = jobCardService.updateJobStatus(jobCardId, jobStatus, driverId);
        if(jobCard != null){
            return new ResponseEntity<>(jobCard, HttpStatus.ACCEPTED);
        }
        return ResponseEntity.notFound().build();
    }
}
