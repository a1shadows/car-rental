package com.navi.rental.service;

import com.navi.rental.dao.interfaces.IBookingDao;
import com.navi.rental.dao.interfaces.IBranchDao;
import com.navi.rental.model.Booking;
import com.navi.rental.service.interfaces.IBranchService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static com.navi.rental.utils.Commons.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@SpringBootTest(args = {"src/main/resources/test.txt"})
class BranchServiceTest {

    @MockBean
    private IBranchDao branchDao;
    @MockBean
    private IBookingDao bookingDao;
    @Autowired
    private IBranchService branchService;

    @BeforeEach
    private void init() {
        given(bookingDao.insertBooking(any())).willAnswer(i -> i.getArguments()[0]);
        given(branchDao.bookVehicle(any(), any(), any())).willReturn(testVehicleBooked);
    }

    @Test
    void book() {
        mockBranchDaoGetsSuccessful();
        mockCheapestVehicleAvailable();
        mockBranchNotOverloaded();
        Booking booking = branchService.book(testBranch.getName(), testVehicle.getType(), 1, 2).get();
        assertEquals(booking.getBranchName(), testBooking.getBranchName());
        assertEquals(booking.getPrice(), testBooking.getPrice());
        assertEquals(booking.getVehicleId(), testBooking.getVehicleId());
    }

    @Test
    @SneakyThrows
    void getPriceWithoutSurge() {
        mockBranchDaoGetsSuccessful();
        mockBranchNotOverloaded();
        assertEquals(branchService.getPrice(testBranch.getName(), testVehicleBooked.getId(), 1, 2), testBooking.getPrice());
    }

    @Test
    @SneakyThrows
    void getPriceWithSurge() {
        mockBranchDaoGetsSuccessful();
        mockBranchOverloaded();
        assertEquals(branchService.getPrice(testBranch.getName(), testVehicleBooked.getId(), 1, 2), Double.valueOf(testBooking.getPrice() * 1.1d));
    }

    private void mockBranchNotOverloaded() {
        given(branchDao.getAvailableVehiclesByBranchOrderedByPrice(any(), any(), any())).willReturn(Arrays.asList(testVehicle, testVehicleBooked, testVehicleCheapest));
        given(branchDao.getAllVehiclesByBranchOrderedByPrice(any(), any(), any())).willReturn(Arrays.asList(testVehicle, testVehicleBooked, testVehicleCheapest));
    }

    private void mockBranchOverloaded() {
        given(branchDao.getAvailableVehiclesByBranchOrderedByPrice(any(), any(), any())).willReturn(Arrays.asList());
        given(branchDao.getAllVehiclesByBranchOrderedByPrice(any(), any(), any())).willReturn(Arrays.asList(testVehicle, testVehicleBooked, testVehicleCheapest));
    }

    private void mockCheapestVehicleAvailable() {
        given(branchDao.getCheapestAvailableVehicleByType(any(), any(), any(), any()))
                .willReturn(Optional.of(testVehicleBooked));
    }

    private void mockBranchDaoGetsSuccessful() {
        given(branchDao.getBranchByName(any())).willReturn(Optional.of(testBranch));
        given(branchDao.getVehicleById(any())).willReturn(Optional.of(testVehicleBooked));
    }

}