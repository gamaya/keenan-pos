package com.keenan.view;

import com.keenan.bean.Product;
import com.keenan.enums.ApplicationView;
import com.keenan.helper.ProductHelper;
import com.keenan.helper.ShoppingCartHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Scanner;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Component
public class ProductEntryView implements Viewable {

    /** Constants **/
    private static final String CANCEL            = "*CANCEL";
    private static final String TOTAL             = "*TOTAL";
    private static final String COMMAND_FOOTER    = "\nEnter Item Number or Command:";
    private static final String NO_ITEMS          = " ** NO ITEMS SELECTED **";
    private static final String PRODUCT_FORMAT    = " %-20s   $ %,7.2f ";
    private static final int PRODUCT_LENGTH    = 12;

    private final List<String> headingData;
    private final Scanner scannerInput;
    private final ShoppingCartHelper shoppingCart;
    private final ProductHelper productHelper;

    private ApplicationView nextView;

    @Autowired
    public ProductEntryView(
            @Qualifier("productEntryViewHeading") final List<String> headingData,
            final ProductHelper productHelper,
            final ShoppingCartHelper shoppingCart,
            final Scanner scannerInput
    ) {
        this.headingData = headingData;
        this.scannerInput = scannerInput;
        this.productHelper = productHelper;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void processView() {

        // Print Heading
        headingData.forEach(System.out::println);

        // Print Product Details Entered
        printProductDetails();

        // Print Command Footer
        System.out.println(COMMAND_FOOTER);

    }

    @Override
    public ApplicationView getNextView() {
        return this.nextView;
    }

    @Override
    public void handleUserInput() {
        final String userInput = scannerInput.nextLine();

        // User Hit Enter, just continue as if nothing happened
        if (StringUtils.isEmpty(userInput)) {
            nextView = ApplicationView.PRODUCT_ENTRY;
            return;
        }

        // String is a command
        if (isCommand(userInput)) {
            handleCommand(userInput);
            return;
        }

        // User may have entered a partial product
        if (userInput.length() < PRODUCT_LENGTH) {
            shoppingCart.setSearchTerm(userInput);
            nextView = ApplicationView.PRODUCT_SEARCH_VIEW;
            return;
        }

        // Try to add product
        addProduct(userInput);
        this.nextView = ApplicationView.PRODUCT_ENTRY;

    }

    private void addProduct(final String product) {

        shoppingCart.getProductList().addAll(productHelper.findProduct(product));

    }

    private void printProductDetails() {

        // Put Blank line after heading
        System.out.println();

        // Print Items
        if (!shoppingCart.getProductList().isEmpty()) {

            for (final Product product : shoppingCart.getProductList()) {

                final String productLine =
                        String.format(PRODUCT_FORMAT, product.getName(), product.getPrice());
                System.out.println(productLine);
            }

        } else {

            // Print Empty List
            System.out.println(NO_ITEMS);
        }

        // Print Empty Line after all items
        System.out.println();

    }

    private boolean isCommand(final String input) {
        return input.equalsIgnoreCase(TOTAL) || input.equalsIgnoreCase(CANCEL);
    }

    private void handleCommand(final String command) {

        if (TOTAL.equalsIgnoreCase(command)) {
            this.nextView = ApplicationView.CHECKOUT;
        }

        else if (CANCEL.equalsIgnoreCase(command)) {
            this.nextView = ApplicationView.MAIN_MENU;
            this.shoppingCart.resetCart();
        }

    }

}
