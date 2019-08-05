package com.keenan.helper;

import com.keenan.bean.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Component
public class ProductHelper {


    private List<Product> productList;

    @Autowired
    public ProductHelper(@Qualifier("productList") final List<Product> productList) {
        this.productList = productList;
    }

    public List<Product> findProduct(final String productId) {
        return productList.stream().filter(p -> p.getId().equalsIgnoreCase(productId)).collect(Collectors.toList());
    }

}
