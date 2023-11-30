package com.bdpq.FormData.model;

import io.swagger.v3.oas.models.info.License;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends Person{
    @OneToMany(mappedBy = "driving_license", cascade = CascadeType.ALL)
    private Set<DriverLicense> license;
    @OneToMany(mappedBy = "vehicle_owner", cascade = CascadeType.ALL)
    private Set<Machinery> machineryList;
}
