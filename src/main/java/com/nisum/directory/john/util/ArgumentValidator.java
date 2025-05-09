package com.nisum.directory.john.util;

import com.nisum.directory.john.dto.request.PhoneRequest;
import com.nisum.directory.john.exception.RequiredValueException;

import java.util.List;
import java.util.Objects;

public class ArgumentValidator {

    private ArgumentValidator() {}

    public static void validateFieldString(String data, String message) {
        validateString(data, message);
    }

    public static void validateFieldPhones(List<PhoneRequest> phones, String message) {
        if (Objects.isNull(phones) || phones.isEmpty()) {
            throw new RequiredValueException(message);
        }
        if (phones.size() > 1) {
            for(PhoneRequest phone : phones ){
                validateString(phone.number(), message);
                validateString(phone.citycode(), message);
                validateString(phone.contrycode(), message);
            }
        }
    }

    private static void validateString(String data, String message) {
        if (Objects.isNull(data) || data.isEmpty()) {
            throw new RequiredValueException(message);
        }
    }

}
