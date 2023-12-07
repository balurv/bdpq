package com.bdpq.FormData.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
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
    @ManyToMany
    private Set<JobCard> jobCards;
    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), super.getName(),super.getPhone(),super.getEmail(),super.getPassword(),super.getCreatedOn(),super.getGender());
    }
}
