package com.keenan.config;

import com.keenan.bean.Product;
import com.keenan.enums.TaxCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Data Configuration
 *
 * Manage data stores used by the application.
 *
 *
 */
@Slf4j
@Configuration
public class DataConfig {

    /** Constants **/
    private static final String PRODUCT_DATA = "product-data.csv";

    private static final int PRODUCT_ID = 0;
    private static final int PRODUCT_NAME = 1;
    private static final int PRICE = 2;
    private static final int TAX_CATEGORY = 3;

    /**
     * loadProductData
     *
     * Load the store's items to a list of Products.
     *
     * @return List of Products
     */
    @Bean("productList")
    public List<Product> loadProductData() {

        final List<Product> productList = new ArrayList<>();
        Stream<String> lines = null;

        try {

            // Load File from Resource Path
            log.info("Loading Product Data File");
            final URI dataUri = getClass().getClassLoader().getResource(PRODUCT_DATA).toURI();
            final Path dataPath = Paths.get(dataUri);

            // Read File and Map to product list
            log.info("Parsing CSV file to Product List");
            lines = Files.lines(dataPath);
            productList.addAll(
                lines.map(line -> line.split(",")).
                    map(lineArray -> Product.builder().
                            id(lineArray[PRODUCT_ID]).
                            name(lineArray[PRODUCT_NAME]).
                            price(BigDecimal.valueOf(Double.parseDouble(lineArray[PRICE]))).
                            taxCategory(TaxCategory.valueOfCode(lineArray[TAX_CATEGORY])).
                            build()).
                    sorted(Comparator.comparing(Product::getId)).
                    collect(Collectors.toList())
            );

        } catch (final Exception exception) {

            log.error("Could not load product file {}", PRODUCT_DATA, exception);
            log.error("No Products Initialized for System");

        } finally {

            // Clean up
            if (lines != null) {
                lines.close();
            }

        }

        if (log.isDebugEnabled()) {
            log.debug("Product Listing:");
            productList.forEach(p -> log.debug(p.toString()));
        }

        return productList;
    }

}
