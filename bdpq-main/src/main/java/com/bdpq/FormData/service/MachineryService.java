package com.bdpq.FormData.service;

import com.bdpq.FormData.model.Machinery;
import com.bdpq.FormData.repository.MachineryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MachineryService {
    @Autowired
    private MachineryRepository machineryRepository;
    public Machinery save(Machinery machinery){
        return machineryRepository.save(machinery);
    }
    public Machinery getById(Long id){
        Optional<Machinery> optionalMachinery = machineryRepository.findById(id);
        if(optionalMachinery.isPresent()){
            return optionalMachinery.get();
        }
        return null;
    }
    public Machinery update(Machinery machinery){
        Optional<Machinery> optionalMachinery = machineryRepository.findById(machinery.getId());
        if(optionalMachinery.isPresent()){
            Machinery dbMachinery = optionalMachinery.get();
            dbMachinery.setMachineryType(machinery.getMachineryType());
            dbMachinery.setName(machinery.getName());
            dbMachinery.setNumber(machinery.getNumber());
            dbMachinery.setVehicle_owner(machinery.getVehicle_owner());

            machineryRepository.save(dbMachinery);
            return dbMachinery;
        }
        return null;
    }
    public Machinery delete(Long id){
        Machinery dbMachiney = getById(id);
        if(dbMachiney != null) {
            machineryRepository.delete(dbMachiney);
        }
        return dbMachiney;
    }
}
