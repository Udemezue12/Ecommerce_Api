package com.uchechukwu.store.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.uchechukwu.store.exceptions.AppException;

@Component
public class RequestValidators {
    public String validateRequestParams(String sort, String sortValue1, String sortValue2) {
        if (!Set.of(sortValue1, sortValue2).contains(sort))
            sort = sortValue1;
        return sort;
    }

    public String validateRequestParam(String sort, String sortValue1) {
        if (!Set.of(sortValue1).contains(sort))
            sort = sortValue1;
        return sort;
    }

    public void throwIfTrue(boolean condition, String message) {

        if (condition) {
            throw new AppException(message);
        }
    }

    

}
