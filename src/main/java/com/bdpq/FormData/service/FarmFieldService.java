package com.bdpq.FormData.service;

import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.repository.FarmFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class FarmFieldService {
    @Autowired
    private FarmFieldRepository farmFieldRepository;
    public FarmField saveFarm(FarmField farmField){
        return farmFieldRepository.save(farmField);
    }

    public Optional<FarmField> getFarmField(Map<String, String> params) {
        // Define filters for each parameter
        Map<String, Predicate<FarmField>> filters = new HashMap<>();
        filters.put("id", farmField -> farmField.getId().equals(Long.parseLong(params.get("id"))));
        filters.put("name", farmField -> farmField.getName().equals(params.get("name")));

        // Create a stream of filters based on provided parameters
        Stream<Predicate<FarmField>> filterStream = params.keySet().stream()
                .filter(filters::containsKey)
                .map(filters::get);

        // Apply filters and return the first matching farm field or empty
        return farmFieldRepository.findAll().stream()
                .filter(filterStream.reduce(x -> false, Predicate::or))
                .findFirst();
    }

    public FarmField updateFarmField(FarmField farmField){
        Map<String, String> params = new HashMap<>();
        params.put("id", farmField.getId().toString());
        Optional<FarmField> dbFarmField = getFarmField(params);
        if(dbFarmField.isPresent()){
            FarmField farmField1 = dbFarmField.get();

            farmField1.setName(farmField.getName());
            farmField1.setLength(farmField.getLength());
            farmField1.setBreath(farmField.getBreath());
            farmField1.setLongitude(farmField.getLongitude());
            farmField1.setLatitude(farmField.getLatitude());
            farmField1.setSoilType(farmField.getSoilType());
            farmField1.setTotalArea(farmField.getTotalArea());
            farmField1.setOwner(farmField.getOwner());
            farmFieldRepository.save(farmField1);
            return farmField1;
        }
        return null;
    }

    public FarmField deleteFarmField(Integer id){
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        Optional<FarmField> dbFarmField = getFarmField(params);
        if(dbFarmField.isPresent()){
            FarmField farmField = dbFarmField.get();
            farmFieldRepository.delete(farmField);
            return farmField;
        }
        return null;
    }

    public Optional<FarmField> getFarmFieldById(Long farmFieldId) {
       return farmFieldRepository.findById(farmFieldId);
    }
}
