package com.bdpq.FormData.dto;

import com.bdpq.FormData.model.DriverLicense;
import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.model.Gender;
import com.bdpq.FormData.model.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonDto {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private LocalDateTime createdOn;
    private Gender gender;
    private Set<Role> role;
    private Set<FarmField> farmFields;
    private Set<DriverLicense> driverLicenses;
}
