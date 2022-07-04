package com.navi.rental.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Vehicle {

    private String id;
    private Double price;
    private String type;

    public enum Status {
        available, booked
    }
}
