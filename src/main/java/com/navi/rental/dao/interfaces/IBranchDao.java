package com.navi.rental.dao.interfaces;

import com.navi.rental.model.Branch;
import com.navi.rental.model.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IBranchDao {

    Optional<Branch> getBranchByName(String name);

    Set<String> getTypesByBranch(String name);

    Optional<Vehicle> getVehicleById(String vehicleId);

    Optional<Vehicle> getCheapestAvailableVehicleByType(String branchName, String vehicleType, Integer startTime, Integer endTime);

    List<Vehicle> getAllVehiclesByBranchOrderedByPrice(String branchName, Integer startTime, Integer endTime);

    List<Vehicle> getVehiclesByBranchAndTypeOrderedByPrice(String branchName, String vehicleType, Integer startTime, Integer endTime);

    List<Vehicle> getAvailableVehiclesByBranchOrderedByPrice(String branchName, Integer startTime, Integer endTime);

    Branch insertBranch(Branch branch, Set<String> vehicleTypes);

    Vehicle insertVehicle(String branchName, Vehicle vehicle);

    Vehicle bookVehicle(String vehicleId, Integer startTime, Integer endTime);
}
