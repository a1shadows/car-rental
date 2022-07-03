package com.navi.rental.service.interfaces;

import com.navi.rental.model.Branch;
import com.navi.rental.model.Vehicle;

import java.util.Optional;
import java.util.Set;

public interface IRentalService extends IBookingService {

    Optional<Branch> onboardBranch(String name, Set<String> vehicleTypes);

    void displayVehicles(String branchName, Integer startTime, Integer endTime);


    void addVehicle(String branchName, Vehicle vehicle);
}
