package com.keenan.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * TaxCategory
 *
 * The tax category that is assigned to an item in the store.
 *
 */
@Getter
public enum TaxCategory {

    CLOTHING("c", "clothing"),
    GROCERIES("g", "groceries"),
    NON_PRESCRIPTION_DRUG("nd","non-prescription drug"),
    OTHER("o", "other items"),
    PREPARED_FOODS("pf", "prepared food"),
    PRESCRIPTION_DRUG("pd","prescription drug");

    private final String code;
    private final String description;

    TaxCategory(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Find the tax category that is associated with the 'Tax Code'.
     * If the code is not associated with an existing tax category,
     * default to `OTHER`.
     *
     *
     * @param code - The Code that is associated to the Tax Category
     * @return TaxCategory
     */
    public static TaxCategory valueOfCode(final String code) {
        return
            Arrays.stream(TaxCategory.values()).
                filter(tc -> tc.code.equalsIgnoreCase(code)).
                findFirst().orElse(OTHER);
    }

}
