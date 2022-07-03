package com.navi.rental.service.interfaces;

import com.navi.rental.model.Branch;

public interface ISurgePricingService {

    default boolean isSurgePricingApplicable(Branch branch, Integer startTime, Integer endTime) {
        return false;
    };

    default Double calculateSurgePrice(Branch branch, Double regularPrice, Integer startTime, Integer endTime) {
        if (isSurgePricingApplicable(branch, startTime, endTime))
            return regularPrice * branch.getSurgeMultiplier();
        else
            return regularPrice;
    }
}
