package com.justedlev.service.phonenumber.service.interfaces;

import com.justedlev.service.phonenumber.api.dto.PhoneNumberDTO;

public interface IPhoneNumberInfo {

    PhoneNumberDTO getInfoByPhoneNumber(String phoneNumber);

}
