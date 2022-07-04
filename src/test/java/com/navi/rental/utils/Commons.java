package com.navi.rental.utils;

import com.navi.rental.model.Booking;
import com.navi.rental.model.Branch;
import com.navi.rental.model.Vehicle;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public final class Commons {

    public static final String TEST_TYPE = "TEST_TYPE";
    public static final Vehicle testVehicleBooked = new Vehicle("TEST3", 300d, TEST_TYPE);
    public static final Vehicle testVehicleCheapest = new Vehicle("TEST2", 200d, TEST_TYPE);
    public static final Vehicle testVehicle = new Vehicle("TEST1", 500d, TEST_TYPE);
    public static final Set<String> testTypes = Collections.singleton(TEST_TYPE);
    public static final String TEST = "TEST";
    public static final Branch testBranch = new Branch(TEST, 1.1d);

    public static final Booking testBooking = new Booking(
            UUID.randomUUID().toString(),
            testVehicleBooked.getId(),
            testBranch.getName(),
            testVehicleBooked.getPrice(),
            1,
            2);

    private Commons() {
        throw new IllegalStateException("Cannot instantiate util class");
    }
}
