package com.keenan.view;

import com.keenan.bean.Product;
import com.keenan.enums.ApplicationView;
import com.keenan.helper.ProductSearchHelper;
import com.keenan.helper.ShoppingCartHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Component
public class ProductSearchView implements Viewable {

    private final ProductSearchHelper productSearchHelper;
    private final ShoppingCartHelper shoppingCart;
    private final Scanner userInput;

    private ApplicationView nextView;
    private List<Product> foundProducts;

    public ProductSearchView(
            final ProductSearchHelper productSearchHelper,
            final ShoppingCartHelper shoppingCart,
            final Scanner userInput
            ) {
        this.shoppingCart = shoppingCart;
        this.productSearchHelper = productSearchHelper;
        this.userInput = userInput;
    }

    @Override
    public void processView() {

        // Reset Product View
        this.nextView = null;

        // Search for Product
        foundProducts = productSearchHelper.findProducts(shoppingCart.getSearchTerm());

        // If we have one Product, add to cart and display cart
        if (foundProducts.size() == 1) {
            shoppingCart.getProductList().add(foundProducts.get(0));
        }

        // Return to Product Entry if we don't need to view results
        if (foundProducts.size() < 2) {
            this.nextView = ApplicationView.PRODUCT_ENTRY;
            return;
        }

        // Write the Heading
        System.out.println("Keenan's Point of Sale");

        System.out.println("\nPlease Enter the line number of the product:\n");

        // Print Our Search Results so the clerk can pick an item
        for (int i = 0; i < foundProducts.size(); i++) {
            final Product product = foundProducts.get(i);
            System.out.println(String.format("\t%s: %s - %s", i+1, product.getId(), product.getName()));
        }

        System.out.println("\n==>");

    }

    @Override
    public ApplicationView getNextView() {
        return this.nextView;
    }

    @Override
    public void handleUserInput() {

        // If we found our product, don't ask the clerk to enter data
        if (this.nextView == ApplicationView.PRODUCT_ENTRY) {
            return;
        }

        final String userSelection = userInput.nextLine();

        try {

            final int selection = Integer.parseInt(userSelection);
            final Product product = foundProducts.get(selection - 1);

            shoppingCart.getProductList().add(product);

            this.nextView = ApplicationView.PRODUCT_ENTRY;

        } catch (Exception e) {
            // User input couldn't be understood, return to product entry
            this.nextView = ApplicationView.PRODUCT_SEARCH_VIEW;
        }

    }
}
