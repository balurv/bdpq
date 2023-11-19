package com.bdpq.FormData.service;

import com.bdpq.FormData.dto.PersonDto;
import com.bdpq.FormData.model.Person;
import com.bdpq.FormData.model.Role;
import com.bdpq.FormData.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.function.Predicate;

@Service
public class PersonService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, String> params = new HashMap<>();
        params.put("name", username);
        Optional<Person> person = getPerson(params);
        if(!person.isPresent()){
            throw new UsernameNotFoundException("user not exists by the email");
        }
        Set<GrantedAuthority> authorities = person.get().getRole().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(username, person.get().getPassword(), authorities);
    }
}
