package com.bdpq.FormData.controller;

import com.bdpq.FormData.model.Role;
import com.bdpq.FormData.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        System.out.println("Hello world");
        Role savedRole = roleService.saveRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getRole(@RequestParam Map<String, String> params){
        Optional<Role> role = roleService.getRole(params);
        if(role.isPresent()){
            return new ResponseEntity<>(role.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("NOT FOUND!", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody Role role){
        Role savedRole = roleService.updateRole(role);
        if(savedRole != null){
            return new ResponseEntity<>(savedRole, HttpStatus.OK);
        }
        return new ResponseEntity<>("Role not Found!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRole(Integer id){
        Role role = roleService.deleteRole(id);
        if(role != null){
            return ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
    }
}
