package com.navi.rental.dao;

import com.navi.rental.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.navi.rental.utils.Commons.*;
import static org.junit.Assert.assertEquals;

public class BookingDaoTest {

    private final BookingDao bookingDao = new BookingDao();

    @BeforeEach
    public void init() {
        bookingDao.init();
        bookingDao.insertBooking(testBooking);
    }

    @Test
    void getBookingById() {
        assertEquals(bookingDao.getBookingById(testBooking.getBookingId()).get(), testBooking);
    }
}
