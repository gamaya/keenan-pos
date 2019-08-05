package com.keenan.view;

import com.keenan.enums.ApplicationView;
import com.keenan.helper.ShoppingCartHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Component
public class CheckoutView implements Viewable {

    private static final String PRODUCT_FORMAT    = " %-20s | $ %,7.2f ";
    private static final String SUMMARY_ITEM      = " %-22s $ %,7.2f";

    private final List<String> templateData;
    private final ShoppingCartHelper shoppingCart;
    private final Scanner scannerInput;
    private ApplicationView nextView;

    @Autowired
    public CheckoutView(
            @Qualifier("checkoutViewTemplate") List<String> templateData,
            final Scanner scannerInput,
            final ShoppingCartHelper shoppingCart
    ) {

        this.templateData = templateData;
        this.shoppingCart = shoppingCart;
        this.scannerInput = scannerInput;

    }

    @Override
    public void processView() {

        // Print Heading from Template
        templateData.forEach(System.out::println);
        System.out.println();

        // Print Product Details Entered
        shoppingCart.getProductList().forEach(product ->
                System.out.println(String.format(PRODUCT_FORMAT, product.getName(), product.getPrice())));
        System.out.println();

        // Print Summary
        printSummary();

        // Show any errors
        if (shoppingCart.hasError()) {
            System.out.println();
            System.out.println(shoppingCart.getErrorMessage());
        }

        // Request Payment
        System.out.println("\nPlease Enter Amount from Customer:");

        shoppingCart.resetErrors();

    }

    @Override
    public ApplicationView getNextView() {
        return this.nextView;
    }

    @Override
    public void handleUserInput() {
        final String userInput = scannerInput.nextLine();

        // Try to convert amount to a number
        try {

            final BigDecimal totalDue = shoppingCart.getSubTotal().add(shoppingCart.getTotalTax());
            final BigDecimal amountTendered = new BigDecimal(userInput);

            if (amountTendered.compareTo(totalDue) < 0) {
                shoppingCart.setErrorMessage(" *** The Amount tendered is not sufficient. ***");
                this.nextView = ApplicationView.CHECKOUT;
            } else {
                shoppingCart.setAmountTendered(amountTendered);
                this.nextView = ApplicationView.RECEIPT_VIEW;
            }

        } catch (Exception e) {
            shoppingCart.setErrorMessage(" *** The entry could not be processed, please try again. ***");
            this.nextView = ApplicationView.CHECKOUT;
        }
    }

    private void printSummary() {

        // Subtotal Current Cart
        final double subTotal = shoppingCart.getSubTotal().doubleValue();

        // Get Taxes
        final double totalTax = shoppingCart.getTotalTax().doubleValue();

        // Print Summaries
        System.out.println("----------------------------------");
        System.out.println(String.format(SUMMARY_ITEM, "SUBTOTAL", subTotal));
        System.out.println(String.format(SUMMARY_ITEM, "TOTAL TAX", totalTax));
        System.out.println("==================================");
        System.out.println(String.format(SUMMARY_ITEM, "AMOUNT DUE", subTotal + totalTax));

    }

}
