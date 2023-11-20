package com.bdpq.FormData.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Setter
@Getter
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
    @JoinColumn(name = "person_id")
    private Person owner;
}
