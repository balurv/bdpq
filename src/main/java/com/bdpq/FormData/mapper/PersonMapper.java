package com.bdpq.FormData.mapper;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.model.Driver;
import com.bdpq.FormData.model.Farmer;

public class PersonMapper {
    public static PersonDto toPersonDto(Farmer person) {
        PersonDto personDto = new PersonDto();
        personDto.setName(person.getName());
        personDto.setGender(person.getGender());
        personDto.setEmail(person.getEmail());
        personDto.setPassword(person.getPassword());
        personDto.setFarmFields(person.getFarmFields());
        personDto.setRole(person.getRole());
        personDto.setPhone(person.getPhone());
        return personDto;
    }

    public static Farmer toPerson(PersonDto personDto) {
        Farmer farmer = new Farmer();
        farmer.setName(personDto.getName());
        farmer.setPhone(personDto.getPhone());
        farmer.setEmail(personDto.getEmail());
        farmer.setPassword(personDto.getPassword());
        farmer.setCreatedOn(personDto.getCreatedOn());
        farmer.setGender(personDto.getGender());
        farmer.setFarmFields(personDto.getFarmFields());
        farmer.setRole(personDto.getRole());
        return farmer;
    }
}
