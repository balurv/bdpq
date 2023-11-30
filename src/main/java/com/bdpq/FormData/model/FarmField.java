package com.bdpq.FormData.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double length;
    private Double breath;
    private Double totalArea;
    private SoilType soilType;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "person_id")
    private Farmer owner;
    @OneToMany (mappedBy = "farmField", cascade = CascadeType.ALL)
    private Set<Image> image;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude, length, breath, totalArea, soilType);
    }
}
