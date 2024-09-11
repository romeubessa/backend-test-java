package com.fcamara.parking.management.exceptions;

import com.fcamara.parking.management.dtos.responses.ErrorResponseDTO;
import com.fcamara.parking.management.utils.ResourceBundleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final ResourceBundleUtils resourceBundleUtils;

    private static final String GENERIC_FIELD_VALIDATION_ERROR = "generic.01";

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ErrorResponseDTO.fromDTO(resourceBundleUtils.getMessage(GENERIC_FIELD_VALIDATION_ERROR,
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField()), GENERIC_FIELD_VALIDATION_ERROR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BusinessRuleException.class})
    public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex) {
        return new ResponseEntity<>(ErrorResponseDTO.fromDTO(resourceBundleUtils.getMessage(ex.getCode(), ex.getArgs()), ex.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ErrorResponseDTO.fromDTO(resourceBundleUtils.getMessage(ex.getCode(), ex.getArgs()), ex.getCode()), HttpStatus.NOT_FOUND);
    }
}
