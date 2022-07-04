package com.navi.rental.service;

import com.navi.rental.service.interfaces.IBranchService;
import com.navi.rental.service.interfaces.IRentalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.navi.rental.utils.Commons.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(args = {"src/main/resources/test.txt"})
public class RentalServiceTest {

    @MockBean
    private IBranchService branchService;
    @Autowired
    private IRentalService rentalService;


    @Test
    void book() {
        given(branchService.book(any(), any(), any(), any())).willReturn(Optional.of(testBooking));
        assertEquals(rentalService.book(testBranch.getName(), testVehicle.getType(), 1, 2).get(), testBooking);
    }
}
