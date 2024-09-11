package com.fcamara.parking.management.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessRuleException extends RuntimeException {

    private String code;
    private Object[] args;

    public BusinessRuleException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public BusinessRuleException(String code) {
        this.code = code;
        this.args = new Object[]{};
    }
}
