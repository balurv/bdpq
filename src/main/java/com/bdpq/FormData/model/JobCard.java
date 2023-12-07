package com.bdpq.FormData.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class JobCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToMany
    private Set<Farmer> farmers;
//    @JsonIgnore
    @ManyToMany
    private Set<FarmField> farmFields;
    private MachineryType machineryType;
    @JsonIgnore
    @ManyToMany
    private Set<Driver> drivers;
    private JobStatus jobStatus;
    @UpdateTimestamp
    private LocalDateTime onCreated;
    @UpdateTimestamp
    private LocalDateTime onClosed;
}
