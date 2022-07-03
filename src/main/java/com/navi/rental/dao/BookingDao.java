package com.navi.rental.dao;

import com.navi.rental.dao.interfaces.IBookingDao;
import com.navi.rental.model.Booking;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookingDao implements IBookingDao {
    private Map<String, Booking> bookingById;

    @PostConstruct
    private void init() {
        bookingById = new HashMap<>();
    }

    @Override
    public Optional<Booking> getBookingById(String bookingId) {
        return Optional.ofNullable(bookingById.get(bookingId));
    }

    @Override
    public Booking insertBooking(Booking booking) {
        bookingById.put(booking.getBookingId(), booking);
        return booking;
    }
}
