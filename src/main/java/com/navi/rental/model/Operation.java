package com.navi.rental.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Operation {
    private Operator operator;
    private String operand;
    private List<String> arguments;

    public Operation(String... input) {
        this.operator = Operator.valueOf(input[0]);
        this.operand = input[1];
        this.arguments = IntStream
                .range(2, input.length)
                .mapToObj(i -> input[i])
                .collect(Collectors.toList());
    }

    public Set<String> getVehicleTypesFromArguments() {
        return Arrays.stream(this.arguments.get(0).split(",")).collect(Collectors.toSet());
    }

    public Vehicle getVehicleFromArguments() {
        return new Vehicle(this.arguments.get(1), Double.valueOf(this.arguments.get(2)), this.arguments.get(0));
        }

    public enum Operator {
        ADD_BRANCH, ADD_VEHICLE, BOOK, DISPLAY_VEHICLES
    }
}
