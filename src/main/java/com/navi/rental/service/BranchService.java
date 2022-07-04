package com.navi.rental.service;

import com.navi.rental.dao.interfaces.IBookingDao;
import com.navi.rental.dao.interfaces.IBranchDao;
import com.navi.rental.exception.CarRentalException;
import com.navi.rental.model.Booking;
import com.navi.rental.model.Branch;
import com.navi.rental.model.Vehicle;
import com.navi.rental.service.interfaces.IBranchService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.navi.rental.exception.CarRentalException.ErrorCode.duplicate_entity;
import static com.navi.rental.exception.CarRentalException.ErrorCode.not_found;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {

    private final IBranchDao branchDao;
    private final IBookingDao bookingDao;

    @Override
    @SneakyThrows
    public Optional<Booking> book(String branchName, String vehicleType, Integer startTime, Integer endTime) {
        Vehicle vehicle = branchDao.getCheapestAvailableVehicleByType(
                branchName,
                vehicleType,
                startTime,
                endTime).orElseThrow(() -> new CarRentalException(not_found, "No available vehicle"));

        branchDao.bookVehicle(vehicle.getId(), startTime, endTime);

        Booking booking = new Booking(
                UUID.randomUUID().toString(),
                vehicle.getId(),
                branchName,
                getPrice(branchName, vehicle.getId(), startTime, endTime),
                startTime,
                endTime
        );
        return Optional.ofNullable(bookingDao.insertBooking(booking));
    }

    @Override
    public Double getPrice(String branchName, String vehicleId, Integer startTime, Integer endTime) throws CarRentalException {
        Branch branch = branchDao.getBranchByName(branchName)
                .orElseThrow(() -> new CarRentalException(not_found, "Branch not found"));
        Vehicle vehicle = branchDao.getVehicleById(vehicleId)
                .orElseThrow(() -> new CarRentalException(not_found, "Vehicle ID not found"));
        double regularPrice = vehicle.getPrice() * (endTime - startTime);
        return calculateSurgePrice(branch, regularPrice, startTime, endTime);
    }

    @Override
    public Branch createBranch(String name, Set<String> vehicleTypes) throws CarRentalException {
        if (branchDao.getBranchByName(name).isPresent())
            throw new CarRentalException(duplicate_entity, "Branch already exists");
        return branchDao.insertBranch(
                new Branch(name, 1.1),
                vehicleTypes
        );

    }

    @Override
    public void addVehicle(String branchName, Vehicle vehicle) throws CarRentalException {
        if (branchDao.getVehicleById(vehicle.getId()).isPresent())
            throw new CarRentalException(duplicate_entity, "Vehicle already exists");
        if (!branchDao.getTypesByBranch(branchName).contains(vehicle.getType()))
            throw new CarRentalException(not_found, "Vehicle type not found in branch");
        branchDao.insertVehicle(branchName, vehicle);
    }

    @Override
    public List<Vehicle> getAvailableVehiclesSortedOnPrice(String branchName, Integer startTime, Integer endTime) {
        return branchDao.getAvailableVehiclesByBranchOrderedByPrice(branchName, startTime, endTime);
    }

    @Override
    public boolean isSurgePricingApplicable(Branch branch, Integer startTime, Integer endTime) {
        if (isBranchOverloaded(branch.getName(), startTime, endTime))
            return true;
        return false;
    }


    private boolean isBranchOverloaded(String branchName, Integer startTime, Integer endTime) {
        return ((double) branchDao.getAvailableVehiclesByBranchOrderedByPrice(branchName, startTime, endTime).stream().count()) /
                ((double) branchDao.getAllVehiclesByBranchOrderedByPrice(branchName, startTime, endTime).stream().count()) <= 0.2;
    }
}
