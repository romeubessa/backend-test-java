package com.fcamara.parking.management.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class ResourceBundleUtils {

    private final ResourceBundle resourceBundle;

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public String getMessage(String key, Object... args) {
        return MessageFormat.format(getMessage(key), args);
    }
}
