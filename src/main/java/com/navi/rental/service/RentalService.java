package com.navi.rental.service;

import com.navi.rental.exception.CarRentalException;
import com.navi.rental.model.Booking;
import com.navi.rental.model.Branch;
import com.navi.rental.model.Vehicle;
import com.navi.rental.service.interfaces.IBranchService;
import com.navi.rental.service.interfaces.IRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalService implements IRentalService {
    private final IBranchService branchService;

    @Override
    public Optional<Booking> book(String branchName, String vehicleType, Integer startTime, Integer endTime) {
        boolean success = true;
        double price = 0;
        try {
            Optional<Booking> booking = branchService.book(branchName, vehicleType, startTime, endTime);
            if (booking.isPresent())
                price = booking.get().getPrice();
            return booking;
        } catch (Exception e) {
            success = false;
            return Optional.empty();
        } finally {
            if (success)
                System.out.println(price);
            else
                System.out.println(-1);
        }
    }

    @Override
    public Double getPrice(String branchName, String vehicleId, Integer startTime, Integer endTime) throws CarRentalException {
        return branchService.getPrice(branchName, vehicleId, startTime, endTime);
    }

    @Override
    public Optional<Branch> onboardBranch(String name, Set<String> vehicleTypes) {
        boolean success = true;
        try {
            return Optional.of(branchService.createBranch(
                    name,
                    vehicleTypes
            ));
        } catch (Exception e) {
            success = false;
            return Optional.empty();
        } finally {
            if (success)
                System.out.println("TRUE");
            else
                System.out.println("FALSE");
        }
    }

    @Override
    public void displayVehicles(String branchName, Integer startTime, Integer endTime) {
        List<Vehicle> availableVehicles = branchService.getAvailableVehiclesSortedOnPrice(branchName, startTime, endTime);
        System.out.println(
                availableVehicles
                        .stream()
                        .map(v -> v.getId())
                        .collect(Collectors.joining(","))
        );
    }

    @Override
    public void addVehicle(String branchName, Vehicle vehicle) {
        boolean success = true;
        try {
            branchService.addVehicle(
                    branchName,
                    vehicle
            );
        } catch (CarRentalException e) {
            success = false;
        } finally {
            if (success)
                System.out.println("TRUE");
            else
                System.out.println("FALSE");
        }
    }
}
