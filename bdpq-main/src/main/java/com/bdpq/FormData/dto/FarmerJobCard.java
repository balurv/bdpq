package com.bdpq.FormData.dto;

import com.bdpq.FormData.model.FarmField;
import com.bdpq.FormData.model.Farmer;
import com.bdpq.FormData.model.Machinery;
import com.bdpq.FormData.model.MachineryType;
import lombok.Data;

@Data
public class FarmerJobCard {
    private Long farmFieldId;
    private MachineryType machinery;
}
