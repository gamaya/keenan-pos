package com.keenan.helper;

/**
 * Product Search Helper Test
 *
 * Test the functionality of the search functions for product lookup
 *
 *
 */

import com.keenan.bean.Product;
import com.keenan.config.DataConfig;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProductSearchHelperTest {

    private DataConfig dataConfig;
    private ProductSearchHelper productSearchHelper;

    @Before
    public void setup() {

        dataConfig = new DataConfig();
        List<Product> productList = dataConfig.loadProductData();
        productSearchHelper = new ProductSearchHelper(productList);

    }

    @Test
    public void test_search_items() {

        // setup
        final String prefix = "30";

        // run tests
        List<Product> searchList = productSearchHelper.findProducts(prefix);

        // assert
        assertEquals("Size of Search items did not match.", 5, searchList.size());

    }

    @Test
    public void test_search_items_from_beginning_of_list() {

        // setup
        final String prefix = "00";

        // run tests
        List<Product> searchList = productSearchHelper.findProducts(prefix);

        // assert
        assertEquals("Size of Search items did not match.", 9, searchList.size());

    }

    @Test
    public void test_specific_search_items() {

        // setup
        final String prefix = "02";

        // run tests
        List<Product> searchList = productSearchHelper.findProducts(prefix);

        // assert
        assertNotNull("Search List result is null", searchList);
        assertEquals("Search List result size is incorrect.", 2, searchList.size());


        assertEquals("Expected item missing from search list result.", "028400157827", searchList.get(0).getId());
        assertEquals("Expected item missing from search list result.", "028400589864", searchList.get(1).getId());

    }

    @Test
    public void test_no_results_found_for_search_term_lower_bound() {

        // setup
        final String prefix = "03";

        // run tests
        List<Product> searchList = productSearchHelper.findProducts(prefix);

        // assert
        assertNotNull("Search list result is null", searchList);
        assertTrue("Search list result isn't empty.", searchList.isEmpty());

    }

    @Test
    public void test_no_results_found_for_search_term_upper_bound() {

        // setup
        final String prefix = "99";

        // run tests
        List<Product> searchList = productSearchHelper.findProducts(prefix);

        // assert
        assertNotNull("Search list result is null", searchList);
        assertTrue("Search list result isn't empty.", searchList.isEmpty());

    }

    @Test
    public void test_no_results_found_for_null_search_term() {

        // setup
        final String prefix = null;

        // run tests
        List<Product> searchList = productSearchHelper.findProducts(prefix);

        // assert
        assertNotNull("Search list result is null", searchList);
        assertTrue("Search list result isn't empty.", searchList.isEmpty());

    }

}
