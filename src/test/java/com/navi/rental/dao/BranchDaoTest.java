package com.navi.rental.dao;

import com.navi.rental.model.Vehicle;
import com.navi.rental.utils.Commons;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BranchDaoTest {
    final BranchDao branchDao = new BranchDao();

    @BeforeEach
    public void init() {
        branchDao.init();
        branchDao.insertBranch(Commons.testBranch, Commons.testTypes);
        branchDao.insertVehicle(Commons.testBranch.getName(), Commons.testVehicle);
        branchDao.insertVehicle(Commons.testBranch.getName(), Commons.testVehicleCheapest);
        branchDao.insertVehicle(Commons.testBranch.getName(), Commons.testVehicleBooked);
    }

    @Test
    void getBranchByName() {
        assertEquals(branchDao.getBranchByName(Commons.TEST).get(),
                Commons.testBranch);
    }

    @Test
    void getTypesByBranch() {
        assertEquals(branchDao.getTypesByBranch(Commons.TEST),
                Commons.testTypes);
    }

    @Test
    void getVehicleById() {
        assertEquals(branchDao.getVehicleById(Commons.testVehicle.getId()).get(),
                Commons.testVehicle);

    }

    @Test
    void getCheapestAvailableVehicleByType() {
        assertEquals(branchDao.getCheapestAvailableVehicleByType(Commons.testBranch.getName(), Commons.TEST_TYPE, 1, 2).get(),
                Commons.testVehicleCheapest);
    }

    @Test
    void getAllVehiclesByBranchOrderedByPrice() {
        assertEquals(branchDao.getAllVehiclesByBranchOrderedByPrice(Commons.testBranch.getName(), 1, 2),
                Arrays.asList(new Vehicle[]{Commons.testVehicleCheapest, Commons.testVehicleBooked, Commons.testVehicle}));
    }

    @Test
    void getVehiclesByBranchAndTypeOrderedByPrice() {
        assertEquals(branchDao.getVehiclesByBranchAndTypeOrderedByPrice(Commons.testBranch.getName(), Commons.TEST_TYPE, 1, 2),
                Arrays.asList(new Vehicle[]{Commons.testVehicleCheapest, Commons.testVehicleBooked, Commons.testVehicle}));
    }

    @Test
    void getAvailableVehiclesByBranchOrderedByPrice() {
        assertEquals(branchDao.getAvailableVehiclesByBranchOrderedByPrice(Commons.testBranch.getName(), 1, 2),
                Arrays.asList(new Vehicle[]{Commons.testVehicleCheapest, Commons.testVehicleBooked, Commons.testVehicle}));
    }

    @Test
    void bookVehicle() {
        branchDao.bookVehicle(Commons.testVehicleBooked.getId(), 1, 2);
        assertEquals(branchDao.getAvailableVehiclesByBranchOrderedByPrice(Commons.testBranch.getName(), 1, 2),
                Arrays.asList(new Vehicle[]{Commons.testVehicleCheapest, Commons.testVehicle}));
    }
}
