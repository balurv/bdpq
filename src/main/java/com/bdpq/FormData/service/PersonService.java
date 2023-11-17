package com.bdpq.FormData.service;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.model.Person;
import com.bdpq.FormData.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

import java.util.HashMap;
import java.util.function.Predicate;

import java.util.Map;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public Person savePerson(Person person){
        return personRepository.save(person);
    }
    public Optional<Person> getPerson(Map<String, String> params) {
        // Define filters for each parameter
        Map<String, Predicate<Person>> filters = new HashMap<>();
            filters.put("id", person -> person.getId().equals(Integer.parseInt(params.get("id"))));
            filters.put("name", person -> person.getName().equals(params.get("name")));
            filters.put("email", person -> person.getEmail().equals(params.get("email")));
            filters.put("phone", person -> person.getPhone().equals(params.get("phone")));
        // Create a stream of filters based on provided parameters
        Stream<Predicate<Person>> filterStream = params.keySet().stream()
                .filter(filters::containsKey)
                .map(filters::get);

        // Apply filters and return the first matching person or empty
        return personRepository.findAll().stream()
                .filter(filterStream.reduce(x -> true, Predicate::and))
                .findFirst();
    }
    public Person updatePerson(PersonDto personDto){
        Map<String, String> params = new HashMap<>();
        params.put("id", personDto.getId().toString());
        Optional<Person> dbPerson = getPerson(params);
        if(dbPerson.isPresent()){
            Person person = dbPerson.get();

            System.out.println("working properly!");
//          update the person
            person.setName(personDto.getName());
            person.setPhone(personDto.getPhone());
            person.setEmail(personDto.getEmail());

//          save the changes in db...
            personRepository.save(person);
            return person;
        }
        return null;
    }

    public Person deletePerson(Integer id){
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        Optional<Person> dbPerson = getPerson(params);
        if(dbPerson.isPresent()){
            Person person = dbPerson.get();
            personRepository.delete(person);
            return person;
        }
        return null;
    }
}
