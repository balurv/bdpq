package com.bdpq.FormData.dto;

import com.bdpq.FormData.model.MachineryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobCardFilterDto {
    private MyLocationDto myLocation;
    private Long range;
    private MachineryType machineryType;
}
