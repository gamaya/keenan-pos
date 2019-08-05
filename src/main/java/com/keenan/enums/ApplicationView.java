package com.keenan.enums;

import com.keenan.view.CheckoutView;
import com.keenan.view.MainView;
import com.keenan.view.ProductEntryView;
import com.keenan.view.ProductSearchView;
import com.keenan.view.ReceiptView;

/**
 * @author <a href="mailto:george.amaya@gmail.com">George Amaya</a>
 */
public enum ApplicationView {

    CHECKOUT(CheckoutView.class.getName()),
    MAIN_MENU(MainView.class.getName()),
    PRODUCT_ENTRY(ProductEntryView.class.getName()),
    RECEIPT_VIEW(ReceiptView.class.getName()),
    PRODUCT_SEARCH_VIEW(ProductSearchView.class.getName());

    private final String className;

    ApplicationView(final String className) {
        this.className = className;
    }

}
