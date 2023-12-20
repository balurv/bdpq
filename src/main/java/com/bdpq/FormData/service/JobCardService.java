package com.bdpq.FormData.service;

import com.bdpq.FormData.dto.FarmerJobCardDto;
import com.bdpq.FormData.dto.JobCardFilterDto;
import com.bdpq.FormData.model.*;
import com.bdpq.FormData.repository.DriverRepository;
import com.bdpq.FormData.repository.JobCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobCardService {
    @Autowired
    private JobCardRepository jobCardRepository;
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private DriverRepository driverRepository;
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

        //need to do some manipulation in caluculating the cost of vehicle;
        double estimatedCost = calculateJobCardCost( optionalFarmField.get(), farmerJobCard.getMachinery());
        jobCard.setEstimatedPayment(estimatedCost);
        jobCardRepository.save(jobCard);
        return jobCard;
    }

    private double calculateJobCardCost(FarmField farmerSet, MachineryType machinery) {
        double area = farmerSet.getTotalArea();
        switch (machinery){
            case CAR, TRACTOR_ROUND_STRAW_BALER -> {
                return area* 1.5;
            }
            case BIKE -> {
                return area* 1.2;
            }
            case TRACTOR -> {
                return area* 1.7;
            }
            case TRACTOR_CULTIVATOR -> {
                return area* 1.8;
            }
            case TRACTOR_ROTAVATOR -> {
                return area*1.99;
            }
            case TRACTOR_PADDY_HARVEST -> {
                return area* 2.2;
            }
            default -> {
                return 0.0;
            }
        }
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
    public List<JobCard> getJobCard(JobCardFilterDto jobCardFilterDto, Pageable pageable) {
        Page<JobCard> jobCardPage = getAllJobCardByStatus(JobStatus.OPEN, pageable);
        List<JobCard> jobCardList = new ArrayList<>();
        for (JobCard jobCard:jobCardPage.getContent()) {
            for (FarmField farmField: jobCard.getFarmFields() ) {
                double distance = haversine(jobCardFilterDto.getMyLocation().getLatitude(),
                        jobCardFilterDto.getMyLocation().getLongitude(),
                        farmField.getLatitude(),
                        farmField.getLongitude()
                );
                if(distance <= jobCardFilterDto.getRange() && jobCard.getMachineryType() == jobCardFilterDto.getMachineryType()){
                    jobCardList.add(jobCard);
                }
            }
        }
        return jobCardList;
    }
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371;  // Earth radius in kilometers

        // Convert latitude and longitude from degrees to radians
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        // Haversine formula
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    public JobCard updateJobStatus(Long jobCardId, JobStatus jobStatus, Long driverId) {
        try{
            Optional<JobCard> optionalJobCard = jobCardRepository.findById(jobCardId);
            Optional<Driver> optionalDriver = driverRepository.findById(driverId);
            if(optionalJobCard.isPresent()){
                JobCard jobCard = optionalJobCard.get();
                Driver driver = optionalDriver.get();

                jobCard.setJobStatus(jobStatus);
                Set<Driver> driverSet = new HashSet<>();
                driverSet.add(driver);
                jobCard.setDrivers(driverSet);
                jobCardRepository.save(jobCard);
                return jobCard;
            }
        }
        catch (Exception e){
            throw e;
        }
        return null;
    }
}
