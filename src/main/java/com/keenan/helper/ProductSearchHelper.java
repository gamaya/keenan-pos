package com.keenan.helper;

import com.keenan.bean.Product;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Component
public class ProductSearchHelper {

    private static final int NOT_FOUND = -1;
    private static final String EMPTY_STRING = "";

    private final List<Product> productList;

    public ProductSearchHelper(final List<Product> productList) {
        this.productList = Collections.unmodifiableList(productList);
    }

    /**
     * Find all products that match our prefix.
     *
     * If no products are found, an empty list will be returned.
     *
     * @param productPrefix - The Prefix of the products to find
     * @return a list of products if found
     */
    public List<Product> findProducts(final String productPrefix) {

        final int startingIndex = findStartingIndex(Optional.ofNullable(productPrefix));
        final List<Product> foundProducts = new LinkedList<>();

        if (startingIndex == NOT_FOUND) {
            return foundProducts;
        }

        // From the starting index, find all products that match the prefix requested
        for (int i = startingIndex; i < productList.size(); i++) {
            final Product product = productList.get(i);
            if (product.getId().startsWith(productPrefix)) {
                foundProducts.add(product);
            } else {
                break;
            }
        }

        return foundProducts;

    }

    /**
     * Find Starting Index of Product Items
     *
     * Using a simple binary search algorithm, look for the first occurrance of an item that starts
     * with the prefix provided. Once a Product is found, walk back through the list of items until
     * we no longer find a match. This is our starting index.
     *
     *
     * @param searchTerm - The prefix to match against product ids
     * @return startingIndex
     */
    private int findStartingIndex(final Optional<String> searchTerm) {

        final String productPrefix = searchTerm.orElse(EMPTY_STRING);
        final int prefixLength = productPrefix.length();
        boolean searching = true;

        // if Size of search is 0, we don't need to look
        if (prefixLength == 0) {
            return NOT_FOUND;
        }

        // Set our search indices to the start values
        int lowerBound = 0;
        int upperBound = productList.size() - 1;
        int searchIndex = upperBound / 2;

        // Continue searching until we find a product or not
        while(searching) {

            String prefix = productList.get(searchIndex).getId().substring(0, prefixLength);

            // Product Prefix equals the prefix we want
            if (productPrefix.equals(prefix)) {
                searching = false;

            // Product Prefix is less than the prefix we want
            } else if (productPrefix.compareTo(prefix) < 0) {
                upperBound = searchIndex - 1;

            // Product Prefix is greater than the prefix we want
            } else {
                lowerBound = searchIndex + 1;
            }

            // Find our next index in the list of products
            searchIndex = (( upperBound - lowerBound ) / 2) + lowerBound;

            // If the search index is not within bounds or equal, stop searching
            if (upperBound == lowerBound) {
                searchIndex = NOT_FOUND;
                searching = false;
            }
        }

        // If the search index was not found, return not found
        if (searchIndex == NOT_FOUND) {
            return NOT_FOUND;
        }

        // Begin walking back through the product to find our starting index
        boolean walkingBack = searchIndex > 0;
        while(walkingBack) {
            if (productList.get(searchIndex - 1).getId().startsWith(productPrefix)) {
                searchIndex--;
                if (searchIndex == 0) {
                    walkingBack = false;
                }
            } else {
                walkingBack = false;
            }
        }

        return searchIndex;
    }

}
