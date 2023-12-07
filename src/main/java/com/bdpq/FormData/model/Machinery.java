package com.bdpq.FormData.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Machinery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String number;
    @Enumerated
    private MachineryType machineryType;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "driver_id")
    private Driver vehicle_owner;

    @Override
    public int hashCode(){
        return Objects.hash(id, name);
    }
}
