package com.navi.rental.dao;

import com.navi.rental.dao.interfaces.IBranchDao;
import com.navi.rental.model.Branch;
import com.navi.rental.model.Vehicle;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;

@Repository
public class BranchDao implements IBranchDao {

    private Map<String, Map<String, List<String>>> vehiclesByTypeByBranch;
    private Map<Integer, Set<String>> vehiclesBySlots;
    private Map<String, Vehicle> vehiclesById;
    private Map<String, Branch> branchesById;

    @PostConstruct
    public void init() {
        vehiclesByTypeByBranch = new HashMap<>();
        vehiclesBySlots = new HashMap<>();
        IntStream.range(1, 24).forEach(i -> vehiclesBySlots.put(i, new HashSet<>()));
        vehiclesById = new HashMap<>();
        branchesById = new HashMap<>();
    }

    @Override
    public Optional<Branch> getBranchByName(String name) {
        return Optional.ofNullable(branchesById.get(name));
    }

    @Override
    public Set<String> getTypesByBranch(String name) {
        return vehiclesByTypeByBranch.get(name).keySet();
    }

    @Override
    public Optional<Vehicle> getVehicleById(String vehicleId) {
        return Optional.ofNullable(vehiclesById.get(vehicleId));
    }

    @Override
    public Optional<Vehicle> getCheapestAvailableVehicleByType(String branchName, String vehicleType, Integer startTime, Integer endTime) {
        return vehiclesByTypeByBranch
                .get(branchName)
                .get(vehicleType)
                .stream()
                .filter(isVehicleAvailable(startTime, endTime))
                .map(vId -> getVehicleById(vId).get())
                .min(Comparator.comparingDouble(Vehicle::getPrice));
    }

    @Override
    public List<Vehicle> getAllVehiclesByBranchOrderedByPrice(String branchName, Integer startTime, Integer endTime) {
        return vehiclesByTypeByBranch
                .get(branchName)
                .values().stream()
                .flatMap(Collection::stream)
                .map(vId -> getVehicleById(vId).get())
                .sorted(Comparator.comparingDouble(Vehicle::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> getVehiclesByBranchAndTypeOrderedByPrice(String branchName, String vehicleType, Integer startTime, Integer endTime) {
        return vehiclesByTypeByBranch
                .get(branchName)
                .get(vehicleType)
                .stream()
                .filter(isVehicleAvailable(startTime, endTime))
                .map(vId -> getVehicleById(vId).get())
                .sorted(Comparator.comparingDouble(Vehicle::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> getAvailableVehiclesByBranchOrderedByPrice(String branchName, Integer startTime, Integer endTime) {
        return vehiclesByTypeByBranch
                .get(branchName)
                .values().stream()
                .flatMap(Collection::stream)
                .filter(isVehicleAvailable(startTime, endTime))
                .map(vId -> getVehicleById(vId).get())
                .sorted(Comparator.comparingDouble(Vehicle::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public Branch insertBranch(Branch branch, Set<String> vehicleTypes) {
        branchesById.put(branch.getName(), branch);
        vehiclesByTypeByBranch.put(branch.getName(),
                vehicleTypes
                        .stream()
                        .collect(Collectors.toMap(Function.identity(), v -> new ArrayList<>()))
        );
        return branch;
    }

    @Override
    public Vehicle insertVehicle(String branchName, Vehicle vehicle) {
        vehiclesById.put(vehicle.getId(), vehicle);
        vehiclesByTypeByBranch.get(branchName).get(vehicle.getType()).add(vehicle.getId());
        return vehicle;
    }

    @Override
    public Vehicle bookVehicle(String vehicleId, Integer startTime, Integer endTime) {
        IntStream.range(startTime, endTime)
                .forEach(slot -> vehiclesBySlots.get(slot).add(vehicleId));
        return vehiclesById.get(vehicleId);
    }

    private Predicate<String> isVehicleAvailable(Integer startTime, Integer endTime) {
        return vehicleId -> {
            for (int slot = startTime; slot < endTime; slot++)
                if (vehiclesBySlots.get(slot).contains(vehicleId))
                    return false;
            return true;
        };
    }
}
