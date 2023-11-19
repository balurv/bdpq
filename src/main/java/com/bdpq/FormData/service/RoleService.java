package com.bdpq.FormData.service;

import com.bdpq.FormData.model.Role;
import com.bdpq.FormData.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }
    public Optional<Role> getRole(Map<String, String>params){
        Map<String, Predicate<Role>> filters = new HashMap<>();
        filters.put("id", role -> role.getId().equals(Integer.parseInt(params.get("id"))));
        filters.put("name", role -> role.getName().equals(params.get("name")));
        Stream<Predicate<Role>> filterStream = params.keySet().stream()
                .filter(filters::containsKey)
                .map(filters::get);
        return roleRepository.findAll().stream()
                .filter(filterStream.reduce( x -> true, Predicate::and))
                .findFirst();
    }
    public Role updateRole(Role role){
        Map<String, String> params = new HashMap<>();
        params.put("name", role.getName());
        Optional<Role> dbRole = getRole(params);
        if(dbRole.isPresent()){
            Role r = dbRole.get();
            r.setName(role.getName());
            roleRepository.save(r);
            return r;
        }
        return null;
    }

    public Role deleteRole(Integer id){
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        Optional<Role> dbRole = getRole(params);
        if(dbRole.isPresent()){
            Role r = dbRole.get();
            roleRepository.delete(r);
            return r;
        }
        return null;
    }
}
