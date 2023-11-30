package com.bdpq.FormData.service;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.model.Driver;
import com.bdpq.FormData.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    public Driver saveDriver(Driver driver){
        return driverRepository.save(driver);
    }
    public Optional<Driver> getDriver(Map<String, String> params){
        Map<String, Predicate<Driver>> filters = new HashMap<>();
        filters.put("id",  person -> person.getId().equals(Integer.parseInt(params.get("id"))));
        filters.put("name", person -> person.getName().equals(params.get("name")));
        filters.put("email", person -> person.getEmail().equals(params.get("email")));
        filters.put("phone", person -> person.getPhone().equals(params.get("phone")));

        // Create a stream of filters based on provided parameters
        Stream<Predicate<Driver>> filterStream = params.keySet().stream()
                .filter(filters::containsKey)
                .map(filters::get);
        return driverRepository.findAll().stream()
                .filter(filterStream.reduce(x -> true, Predicate::and))
                .findFirst();
    }
    public Driver updateDriver(Driver personDto){
        Map<String, String> params = new HashMap<>();
        params.put("id", personDto.getId().toString());
        Optional<Driver> dbDriver = getDriver(params);
        if(dbDriver.isPresent()){
            Driver person = dbDriver.get();
            updateFieldIfNotNull(personDto.getEmail(), person::setEmail);
            updateFieldIfNotNull(personDto.getPassword(), person::setPassword);
            updateFieldIfNotNull(personDto.getName(), person::setName);
            updateFieldIfNotNull(personDto.getGender(), person::setGender);
            updateFieldIfNotNull(personDto.getPhone(), person::setPhone);
            driverRepository.save(person);
            return person;
        }
        return null;
    }

    private static <T> void updateFieldIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    public Driver deleteDriver(Integer id){
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        Optional<Driver> dbPerson = getDriver(params);
        if(dbPerson.isPresent()){
            Driver driver = dbPerson.get();
            driverRepository.delete(driver);
            return driver;
        }
        return null;
    }
}
