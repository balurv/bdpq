package com.bdpq.FormData.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String licenseNumber;
    private LocalDate dob;
    private MachinaryType machineryType;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "person_id")
    private Driver driving_license;
}
