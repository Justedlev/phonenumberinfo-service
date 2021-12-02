package com.justedlev.service.phonenumber.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PhoneNumberDTO {

    private String phone_number;
    private Integer country_code;
    private Long national_number;
    private String region;
    private String number_type;
    private String location;
    private List<String> time_zones;

}
