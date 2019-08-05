package com.keenan.enums;


import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public enum TaxRate {

    STATE(0.063, "state", Collections.emptyList()),
    COUNTY(0.007, "county", Collections.singletonList(TaxCategory.GROCERIES)),
    CITY(0.02, "city", Collections.emptyList());

    private final Double rate;
    private final String description;
    private final List<TaxCategory> excludeCategories;

    TaxRate(final Double rate, final String description, final List<TaxCategory> excludeCategories) {
        this.rate = rate;
        this.description = description;
        this.excludeCategories = excludeCategories;
    }

}
