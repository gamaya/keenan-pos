package com.keenan.bean;

import com.keenan.enums.TaxCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Product
 *
 * An item that is sold in the market.
 *
 */
@Builder
@Data
public class Product {

    private final String id;
    private final String name;
    private final BigDecimal price;
    private final TaxCategory taxCategory;

}


