package com.fcamara.parking.management.services;

import com.fcamara.parking.management.models.ParkingTransaction;

public interface ParkingTransactionService {

    ParkingTransaction registerEntry(String establishmentId, String plate);

    ParkingTransaction registerExit(String establishmentId, String plate);
}
