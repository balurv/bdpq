package com.bdpq.FormData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyLocationDto {
    private Double latitude;
    private Double longitude;
}
