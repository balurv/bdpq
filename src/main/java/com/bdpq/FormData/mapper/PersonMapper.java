package com.bdpq.FormData.mapper;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.model.Person;

public class PersonMapper {
    public static PersonDto toPersonDto(Person person) {
        return new PersonDto(
                person.getId(),
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getPassword(),
                person.getCreatedOn(),
                person.getGender(),
                person.getRole(),
                person.getFarmFields()
        );
    }

    public static Person toPerson(PersonDto personDto) {
        return new Person(
                personDto.getId(),
                personDto.getName(),
                personDto.getPhone(),
                personDto.getEmail(),
                personDto.getPassword(),
                personDto.getCreatedOn(),
                personDto.getGender(),
                personDto.getRole(),
                personDto.getFarmFields()
        );
    }
}
