package com.navi.rental.dao.interfaces;

import com.navi.rental.model.Booking;

import java.util.Optional;

public interface IBookingDao {

    Optional<Booking> getBookingById(String bookingId);

    Booking insertBooking(Booking booking);

}
