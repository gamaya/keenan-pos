package com.keenan.service;

import com.keenan.bean.Product;
import com.keenan.enums.TaxRate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TaxService {


    public BigDecimal calculateTax(final TaxRate taxRate, final List<Product> productList) {

        // Initialize Rate for the Tax Rate Requested
        final BigDecimal rate = new BigDecimal(taxRate.getRate());

        // For Empty List, return ZERO
        if (CollectionUtils.isEmpty(productList)) {
            return BigDecimal.ZERO.setScale(2);
        }

        // Calculate the tax for each product excluding categories that may not have tax
        final BigDecimal totalTax = productList.stream().
                                        filter(product -> !taxRate.getExcludeCategories().contains(product.getTaxCategory())).
                                        map(product -> product.getPrice().multiply(rate)).
                                        reduce(BigDecimal.ZERO, (subtotal, productTax) -> subtotal.add(productTax));

        return totalTax.setScale(2, RoundingMode.HALF_UP);

    }

}
