package com.justedlev.service.phonenumber.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToTimeZonesMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import com.justedlev.service.phonenumber.api.ServiceNamesConstants;
import com.justedlev.service.phonenumber.api.dto.PhoneNumberDTO;
import com.justedlev.service.phonenumber.service.interfaces.IPhoneNumberInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@Qualifier(ServiceNamesConstants.PHONE_NUMBER_SERVICE)
public class PhoneNumberInfoService implements IPhoneNumberInfo {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    @Override
    public PhoneNumberDTO getInfoByPhoneNumber(String phoneNumber) {
        log.debug("Received phone number : {}", phoneNumber);
        PhoneNumber pn;
        PhoneNumberOfflineGeocoder pnog = PhoneNumberOfflineGeocoder.getInstance();
        try {
            log.debug("Parsing phone number : {}", phoneNumber);
            pn = phoneNumberUtil.parse(phoneNumber, null);
        } catch (NumberParseException e) {
            log.debug("Failed to parse phone number : {}", e.getMessage());
            return PhoneNumberDTO.builder()
                    .input_phone_number(phoneNumber)
                    .is_number_valid(false)
                    .build();
        }
        if (!phoneNumberUtil.isValidNumber(pn)) {
            log.debug("Phone number is not valid");
            return PhoneNumberDTO.builder()
                    .input_phone_number(phoneNumber)
                    .is_number_valid(false)
                    .build();
        }
        List<String> timeZones = PhoneNumberToTimeZonesMapper.getInstance().getTimeZonesForNumber(pn);
        PhoneNumberDTO phoneNumberDTO = PhoneNumberDTO.builder()
                .input_phone_number(phoneNumber)
                .country_code(pn.getCountryCode())
                .national_number(pn.getNationalNumber())
                .region(phoneNumberUtil.getRegionCodeForNumber(pn))
                .number_type(phoneNumberUtil.getNumberType(pn).toString())
                .location(pnog.getDescriptionForNumber(pn, Locale.ENGLISH))
                .time_zones(timeZones)
                .is_number_valid(true)
                .build();
        log.debug("Received info about the phone number : {}", phoneNumberDTO);
        return phoneNumberDTO;
    }

}
