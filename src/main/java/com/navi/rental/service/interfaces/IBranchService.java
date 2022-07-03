package com.navi.rental.service.interfaces;

import com.navi.rental.exception.CarRentalException;
import com.navi.rental.model.Branch;
import com.navi.rental.model.Vehicle;

import java.util.List;
import java.util.Set;

public interface IBranchService extends IBookingService {

    Branch createBranch(String name, Set<String> vehicleTypes) throws CarRentalException;

    void addVehicle(String branchName, Vehicle vehicle) throws CarRentalException;

    List<Vehicle> getAvailableVehiclesSortedOnPrice(String branchName, Integer startTime, Integer endTime);
}
