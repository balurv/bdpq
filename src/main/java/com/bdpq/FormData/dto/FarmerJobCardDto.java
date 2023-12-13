package com.bdpq.FormData.dto;

import com.bdpq.FormData.model.MachineryType;
import lombok.Data;

@Data
public class FarmerJobCardDto {
    private Long farmFieldId;
    private MachineryType machinery;
}
