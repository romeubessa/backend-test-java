package com.fcamara.parking.management.enums;

import com.fcamara.parking.management.exceptions.BusinessRuleException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.fcamara.parking.management.enums.MessagesEnum.PHISICAL_VEHICLE_02;

@Getter
@AllArgsConstructor
public enum VehicleTypeEnum {

    CAR("Car"),
    MOTORCYCLE("Motorcycle");

    private final String description;

    public static VehicleTypeEnum getVehicleType(String description) {
        try {
            return VehicleTypeEnum.valueOf(description.toUpperCase());
        } catch (IllegalArgumentException e) {
            var validTypes = Arrays.stream(VehicleTypeEnum.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));

            throw new BusinessRuleException(PHISICAL_VEHICLE_02.getCode(), validTypes);
        }
    }
}
