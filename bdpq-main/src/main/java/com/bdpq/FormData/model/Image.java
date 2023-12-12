package com.bdpq.FormData.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String path;
    @ManyToOne
    @JoinColumn(name = "farm_field_id")
    private FarmField farmField;
}
