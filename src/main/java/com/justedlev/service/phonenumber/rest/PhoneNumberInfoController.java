package com.justedlev.service.phonenumber.rest;

import com.justedlev.service.phonenumber.api.PathConstants;
import com.justedlev.service.phonenumber.api.ServiceNamesConstants;
import com.justedlev.service.phonenumber.api.dto.PhoneNumberDTO;
import com.justedlev.service.phonenumber.service.interfaces.IPhoneNumberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneNumberInfoController {

    @Autowired
    @Qualifier(ServiceNamesConstants.PHONE_NUMBER_SERVICE)
    private IPhoneNumberInfo service;

    @GetMapping(PathConstants.PHONE_NUMBER_PATH)
    public ResponseEntity<PhoneNumberDTO> getAirlineDestinationsByAircraft(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(service.getInfoByPhoneNumber(phoneNumber));
    }

}
