package com.navi.rental.service.interfaces;

import com.navi.rental.exception.CarRentalException;
import com.navi.rental.model.Booking;

import java.util.Optional;

public interface IBookingService extends ISurgePricingService {

    Optional<Booking> book(String branchName, String vehicleType, Integer startTime, Integer endTime);

    Double getPrice(String branchName, String vehicleId, Integer startTime, Integer endTime) throws CarRentalException;
}
