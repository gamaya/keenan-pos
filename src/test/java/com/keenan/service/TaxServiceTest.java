package com.keenan.service;

import com.keenan.bean.Product;
import com.keenan.enums.TaxCategory;
import com.keenan.enums.TaxRate;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaxServiceTest {

    private TaxService taxService;
    private List<Product> productList;

    @Before
    public void setup() {

        // Initialize Tax Service
        taxService = new TaxService();

        // Initialize List of Items for Testing
        productList = new ArrayList();

        // Taxable Product
        productList.add(
            Product.builder().
                id("000000123456").
                name("t-shirt").
                taxCategory(TaxCategory.CLOTHING).
                price(BigDecimal.valueOf(12.99)).
                build());

        // Taxable Product
        productList.add(
            Product.builder().
                id("000000123457").
                name("hot food item").
                taxCategory(TaxCategory.PREPARED_FOODS).
                price(BigDecimal.valueOf(3.99)).
                build());

        // Non-Taxable Product (County Only)
        productList.add(
            Product.builder().
                id("000000123458").
                name("ramen noodles").
                taxCategory(TaxCategory.GROCERIES).
                price(BigDecimal.valueOf(13.50)).
                build());

        // Unknown Tax Category (Default to Other)
        productList.add(
                Product.builder().
                        id("000000123459").
                        name("misc item").
                        price(BigDecimal.valueOf(7.49)).
                        build());

    }

    @Test
    public void test_tax_calculation_for_state() {

        // Setup Tests
        // Expected Tax: (0.81 + 0.25 + 0.85 + 0.47) == 2.39
        final BigDecimal expectedTax = BigDecimal.valueOf(2.39);

        // Run Test
        final BigDecimal actualTax = taxService.calculateTax(TaxRate.STATE, productList);

        // Assert
        assertEquals("State Tax Calculation is incorrect", expectedTax, actualTax);

    }

    @Test
    public void test_tax_calculation_for_county() {

        // Setup Tests
        // Expected Tax: (0.09 + 0.03 + 0.05) == 0.17
        final BigDecimal expectedTax = BigDecimal.valueOf(0.17);

        // Run Test
        final BigDecimal actualTax = taxService.calculateTax(TaxRate.COUNTY, productList);

        // Assert
        assertEquals("State Tax Calculation is incorrect", expectedTax, actualTax);

    }

    @Test
    public void test_tax_calculation_for_city() {

        // Setup Tests
            // Expected Tax: (0.0.26 + 0.08 + 0.27 + 0.15) == 0.76
        final BigDecimal expectedTax = BigDecimal.valueOf(0.76);

        // Run Test
        final BigDecimal actualTax = taxService.calculateTax(TaxRate.CITY, productList);

        // Assert
        assertEquals("State Tax Calculation is incorrect", expectedTax, actualTax);

    }

    @Test
    public void test_tax_calculation_empty_list() {

        // Setup Tests
        final List<Product> emptyList = Collections.emptyList();

        // Run Test
        final BigDecimal actualTax = taxService.calculateTax(TaxRate.CITY, emptyList);

        // Assert
        assertEquals("Empty List is not zero", BigDecimal.ZERO.setScale(2), actualTax);

    }

    @Test
    public void test_tax_calculation_null_list() {

        // Setup Tests
        final List<Product> nullList = null;

        // Run Test
        final BigDecimal actualTax = taxService.calculateTax(TaxRate.CITY, nullList);

        // Assert
        assertEquals("Null List is not zero", BigDecimal.ZERO.setScale(2), actualTax);

    }

}
