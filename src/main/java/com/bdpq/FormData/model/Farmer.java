package com.bdpq.FormData.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "farmer")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("farmFields")
public class Farmer extends Person {
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Role> role;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<FarmField> farmFields;

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), super.getName(),super.getPhone(),super.getEmail(),super.getPassword(),super.getCreatedOn(),super.getGender());
    }

}
