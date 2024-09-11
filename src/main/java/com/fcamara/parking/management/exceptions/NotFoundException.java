package com.fcamara.parking.management.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends RuntimeException {

    private String code;
    private Object[] args;

    public NotFoundException(String code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public NotFoundException(String code) {
        this.code = code;
        this.args = new Object[]{};
    }
}
