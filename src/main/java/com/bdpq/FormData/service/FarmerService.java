package com.bdpq.FormData.service;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.model.Farmer;
import com.bdpq.FormData.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.function.Predicate;

@Service
public class FarmerService implements UserDetailsService {

    @Autowired
    private FarmerRepository personRepository;

    public Farmer saveFarmer(Farmer person){
        return personRepository.save(person);
    }
    public Optional<Farmer> getFarmer(Map<String, String> params) {
        // Define filters for each parameter
        Map<String, Predicate<Farmer>> filters = new HashMap<>();
            filters.put("id", person -> person.getId().equals(Integer.parseInt(params.get("id"))));
            filters.put("name", person -> person.getName().equals(params.get("name")));
            filters.put("email", person -> person.getEmail().equals(params.get("email")));
            filters.put("phone", person -> person.getPhone().equals(params.get("phone")));
        // Create a stream of filters based on provided parameters
        Stream<Predicate<Farmer>> filterStream = params.keySet().stream()
                .filter(filters::containsKey)
                .map(filters::get);

        // Apply filters and return the first matching person or empty
        return personRepository.findAll().stream()
                .filter(filterStream.reduce(x -> true, Predicate::and))
                .findFirst();
    }
    public Farmer updateFarmer(PersonDto personDto){
        Map<String, String> params = new HashMap<>();
        params.put("id", personDto.getId().toString());
        Optional<Farmer> dbPerson = getFarmer(params);
        if(dbPerson.isPresent()){
            Farmer person = dbPerson.get();

            updateFieldIfNotNull(personDto.getEmail(), person::setEmail);
            updateFieldIfNotNull(personDto.getRole(), person::setRole);
            updateFieldIfNotNull(personDto.getPassword(), person::setPassword);
            updateFieldIfNotNull(personDto.getName(), person::setName);
            updateFieldIfNotNull(personDto.getFarmFields(), fields -> {
                fields.forEach(field -> field.setOwner(person));
                person.setFarmFields(fields);
            });
            updateFieldIfNotNull(personDto.getGender(), person::setGender);
            updateFieldIfNotNull(personDto.getPhone(), person::setPhone);

            // save the changes in db...
            personRepository.save(person);
            return person;
        }
        return null;
    }


    public Farmer deleteFarmer(Integer id){
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        Optional<Farmer> dbPerson = getFarmer(params);
        if(dbPerson.isPresent()){
            Farmer person = dbPerson.get();
            personRepository.delete(person);
            return person;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, String> params = new HashMap<>();
        params.put("name", username);
        Optional<Farmer> person = getFarmer(params);
        if(!person.isPresent()){
            throw new UsernameNotFoundException("user not exists by the email");
        }
        Set<GrantedAuthority> authorities = person.get().getRole().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(username, person.get().getPassword(), authorities);
    }

    private static <T> void updateFieldIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    public Farmer updateFarmerFarmField(Long farmerId, FarmField farmField) {
        Map<String, String> param = new HashMap<>();
        param.put("id", farmerId.toString());
        Optional<Farmer> optionalFarmer = getFarmer(param);
        if (optionalFarmer.isPresent()) {
            Farmer farmer = optionalFarmer.get();
            farmField.setOwner(farmer);
            Set<FarmField> existingFarmFields = farmer.getFarmFields();

            // Add the new farm field to the existing set of farm fields
            existingFarmFields.add(farmField);

            // Update the farmer's farm fields
            farmer.setFarmFields(existingFarmFields);

            // Save the updated farmer to the repository
            personRepository.save(farmer);
            return farmer;
        }
        return null;
    }

}
