package com.bdpq.FormData.service;

import com.bdpq.FormData.dto.FarmerJobCardDto;
import com.bdpq.FormData.dto.JobCardFilterDto;
import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.model.Farmer;
import com.bdpq.FormData.model.JobCard;
import com.bdpq.FormData.model.JobStatus;
import com.bdpq.FormData.repository.JobCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class JobCardService {
    @Autowired
    private JobCardRepository jobCardRepository;
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private FarmFieldService farmFieldService;

    public JobCard save(JobCard jobCard) {
        return jobCardRepository.save(jobCard);
    }

    public JobCard get(Long jobCardId) {
        Optional<JobCard> optionalJobCard = jobCardRepository.findById(jobCardId);
        if (optionalJobCard.isPresent()) {
            return optionalJobCard.get();
        }
        return null;
    }


    //    public JobCard update(JobCard jobCard){
//        Optional<JobCard> optionalJobCard = jobCardRepository.findById(jobCard.getId());
//        if(optionalJobCard.isPresent()){
//            JobCard dbJobCard = optionalJobCard.get();
//            dbJobCard.setJobStatus(jobCard.getJobStatus());
//            dbJobCard.setFarmers(job);
//        }
//    }
    public JobCard delete(Long id) {
        JobCard jobCard = get(id);
        if (jobCard != null) {
            jobCardRepository.delete(jobCard);
        }
        return jobCard;
    }

    public JobCard create(FarmerJobCardDto farmerJobCard) {
        JobCard jobCard = new JobCard();

        Set<Farmer> farmerSet = new HashSet<>();


        Set<FarmField> farmFieldSet = new HashSet<>();
        Optional<FarmField> optionalFarmField = farmFieldService.getFarmFieldById(farmerJobCard.getFarmFieldId());
        if (optionalFarmField.isPresent()) {
            FarmField farmField = optionalFarmField.get();
            farmFieldSet.add(farmField);
            Optional<Farmer> optionalFarmer = farmerService.getFarmerById(farmField.getOwner().getId());
            farmerSet.add(optionalFarmer.get());
        }
        jobCard.setFarmers(farmerSet);
        jobCard.setFarmFields(farmFieldSet);
        jobCard.setMachineryType(farmerJobCard.getMachinery());
        jobCard.setJobStatus(JobStatus.OPEN);
        jobCardRepository.save(jobCard);
        return jobCard;
    }

    public Page<JobCard> getJobCardByStatus(JobStatus jobStatus, Long farmerId, Pageable pageable) {
        return jobCardRepository.getJobCard(farmerId, jobStatus, pageable);
    }

    public Page<JobCard> getJobCard(Long farmerId, Pageable pageable) {
        return jobCardRepository.getJobCard(farmerId, pageable);
    }
    public Page<JobCard> getAllJobCardByStatus(JobStatus jobStatus, Pageable pageable){
        return jobCardRepository.getJobCardsByStatus(jobStatus, pageable);
    }
    public Page<JobCard> getJobCard(JobCardFilterDto jobCardFilterDto, Pageable pageable) {
        return getAllJobCardByStatus(JobStatus.OPEN, pageable);
    }
}
