package com.keenan.config;


import com.keenan.enums.ApplicationView;
import com.keenan.view.CheckoutView;
import com.keenan.view.MainView;
import com.keenan.view.ProductEntryView;
import com.keenan.view.ProductSearchView;
import com.keenan.view.ReceiptView;
import com.keenan.view.Viewable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class ViewConfig {

    /** Constants **/
    private static final String VIEW_FORMAT = "%s/%s";
    private static final String CHECKOUT_VIEW = "checkout.view";
    private static final String VIEW_PATH = "view";
    private static final String MAIN_VIEW = "main.view";
    private static final String PRODUCT_ENTRY_HEADING_VIEW = "product.entry.heading.view";
    private static final String RECEIPT_VIEW = "receipt.view";

    @Bean
    public Map<ApplicationView, Viewable> applicationViewableMap(
            final MainView mainView,
            final ProductEntryView productEntryView,
            final CheckoutView checkoutView,
            final ReceiptView receiptView,
            final ProductSearchView productSearchView
            ) {

        Map<ApplicationView, Viewable> applicationViewViewableMap = new HashMap<>();

        applicationViewViewableMap.put(ApplicationView.MAIN_MENU, mainView);
        applicationViewViewableMap.put(ApplicationView.PRODUCT_ENTRY, productEntryView);
        applicationViewViewableMap.put(ApplicationView.CHECKOUT, checkoutView);
        applicationViewViewableMap.put(ApplicationView.RECEIPT_VIEW, receiptView);
        applicationViewViewableMap.put(ApplicationView.PRODUCT_SEARCH_VIEW, productSearchView);

        return applicationViewViewableMap;

    }

    @Bean("mainViewTemplate")
    public List<String> mainViewTemplate() throws Exception {
        return loadTemplate(MAIN_VIEW);
    }

    @Bean("productEntryViewHeading")
    public List<String> productEntryViewTemplate() throws Exception {
        return loadTemplate(PRODUCT_ENTRY_HEADING_VIEW);
    }

    @Bean("checkoutViewTemplate")
    public List<String> checkoutViewTemplate() throws Exception {
        return loadTemplate(CHECKOUT_VIEW);
    }

    @Bean("receiptViewTemplate")
    public List<String> receiptViewTemplate() throws Exception {
        return loadTemplate(RECEIPT_VIEW);
    }

    @Bean
    public Scanner scannerInput() {
        return new Scanner(System.in);
    }

    private List<String> loadTemplate(final String templateName) throws Exception {
        final String view = String.format(VIEW_FORMAT, VIEW_PATH, templateName);
        final URI viewUri = getClass().getClassLoader().getResource(view).toURI();
        final Path viewPath = Paths.get(viewUri);

        return Files.readAllLines(viewPath);
    }

}
