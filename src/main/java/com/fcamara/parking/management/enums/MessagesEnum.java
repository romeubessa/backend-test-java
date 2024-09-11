package com.fcamara.parking.management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessagesEnum {

    PHISICAL_VEHICLE_01("phisical.vehicle.01"),
    PHISICAL_VEHICLE_02("phisical.vehicle.02"),
    PHISICAL_VEHICLE_03("phisical.vehicle.03"),
    PHISICAL_VEHICLE_04("phisical.vehicle.04"),

    PHISICAL_ESTABLISHMENT_01("phisical.establishment.01"),
    PHISICAL_ESTABLISHMENT_02("phisical.establishment.02"),

    PHISICAL_PARKING_01("phisical.parking.01"),
    PHISICAL_PARKING_02("phisical.parking.02"),
    PHISICAL_PARKING_03("phisical.parking.03"),

    GENERIC_01("generic.01");

    private final String code;
}
