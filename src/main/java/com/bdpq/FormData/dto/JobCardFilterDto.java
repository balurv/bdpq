package com.bdpq.FormData.dto;

import com.bdpq.FormData.model.MachineryType;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobCardFilterDto {
    private MyLocationDto myLocation;
    private Long range;
    private MachineryType machineryType;
}
