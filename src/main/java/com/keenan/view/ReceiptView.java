package com.keenan.view;

import com.keenan.enums.ApplicationView;
import com.keenan.helper.ShoppingCartHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
@Component
public class ReceiptView implements Viewable {

    private static final String PRODUCT_FORMAT = " %-22s  %s   $ %,7.2f %s";
    private static final String SUMMARY_FORMAT = " %-38s $ %,7.2f";

    private List<String> headingData;
    private ShoppingCartHelper shoppingCart;
    private Scanner userInput;
    private ApplicationView nextView;

    public ReceiptView(
        @Qualifier("receiptViewTemplate") final List<String> headingData,
        ShoppingCartHelper shoppingCart,
        final Scanner userInput
    ) {
        this.headingData = headingData;
        this.shoppingCart = shoppingCart;
        this.userInput = userInput;
    }

    @Override
    public void processView() {

        // Print Heading
        headingData.forEach(System.out::println);

        // Print Product Summary
        shoppingCart.getProductList().stream().
                map(product ->
                        String.format(PRODUCT_FORMAT,
                                        product.getName(),
                                        product.getId(),
                                        product.getPrice(),
                                        product.getTaxCategory().getCode()
                        )).
                forEach(System.out::println);

        // Print Cart Subtotal
        System.out.println("----------------------------------------------------");
        System.out.println(String.format(SUMMARY_FORMAT, "SUBTOTAL", shoppingCart.getSubTotal()));

        // Print Cart Tax
        System.out.println(String.format(SUMMARY_FORMAT, "STATE TAX", shoppingCart.getStateTax()));
        System.out.println(String.format(SUMMARY_FORMAT, "COUNTY TAX", shoppingCart.getCountyTax()));
        System.out.println(String.format(SUMMARY_FORMAT, "CITY TAX", shoppingCart.getCityTax()));

        // Print Total Due
        final BigDecimal totalDue = shoppingCart.getSubTotal().add(shoppingCart.getTotalTax());
        System.out.println("====================================================");
        System.out.println("\n" + String.format(SUMMARY_FORMAT, "TOTAL DUE", totalDue));

        // Print Amount Paid
        System.out.println();
        System.out.println(String.format(SUMMARY_FORMAT, "AMOUNT TENDERED", shoppingCart.getAmountTendered()));

        // Print Change Due
        final BigDecimal changeDue = totalDue.subtract(shoppingCart.getAmountTendered()).abs();
        System.out.println(String.format(SUMMARY_FORMAT, "CHANGE DUE", changeDue));

        // Press Enter to Continue
        System.out.println("\n\nPRESS ENTER TO CONTINUE");

    }

    @Override
    public void handleUserInput() {

        // Wait for User Input...
        // Doesn't matter what key they push, we will continue to main screen for next sale
        userInput.nextLine();

        // Reset the Shopping cart for next sale
        shoppingCart.resetCart();
        this.nextView = ApplicationView.MAIN_MENU;
    }

    @Override
    public ApplicationView getNextView() {
        return this.nextView;
    }

}
