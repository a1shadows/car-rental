package com.navi.rental.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Booking {

    private String bookingId;
    private String vehicleId;
    private String branchName;
    private Double price;
    private Integer startTime;
    private Integer endTime;

}
