package com.keenan.helper;

import com.keenan.bean.Product;
import com.keenan.enums.TaxRate;
import com.keenan.service.TaxService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Getter
@Setter
@Component
public class ShoppingCartHelper {

    private List<Product> productList;
    private BigDecimal stateTax;
    private BigDecimal cityTax;
    private BigDecimal countyTax;

    private String searchTerm;

    private BigDecimal amountTendered;

    private String errorMessage;

    private final TaxService taxService;

    public ShoppingCartHelper(final TaxService taxService) {
        this.taxService = taxService;
        this.productList = new LinkedList<>();
    }

    public void resetCart() {
        productList = new LinkedList<>();
        stateTax = BigDecimal.ZERO;
        cityTax = BigDecimal.ZERO;
        countyTax = BigDecimal.ZERO;
        errorMessage = null;
    }

    public void resetErrors() {
        this.errorMessage = null;
    }

    public BigDecimal getSubTotal() {
        return
            productList.stream().
                    map(product -> product.getPrice()).
                    reduce(BigDecimal.ZERO, (subtotal, price) -> subtotal.add(price)).
                    setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalTax() {
        calculateTaxes();
        return stateTax.add(countyTax).add(cityTax);
    }

    public boolean hasError() {
        return errorMessage != null;
    }

    private void calculateTaxes() {
        this.stateTax = taxService.calculateTax(TaxRate.STATE, this.productList).setScale(2, RoundingMode.HALF_UP);
        this.countyTax = taxService.calculateTax(TaxRate.COUNTY, this.productList).setScale(2, RoundingMode.HALF_UP);
        this.cityTax = taxService.calculateTax(TaxRate.CITY, this.productList).setScale(2, RoundingMode.HALF_UP);
    }



}
