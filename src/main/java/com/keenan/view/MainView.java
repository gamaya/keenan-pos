package com.keenan.view;

import com.keenan.enums.ApplicationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

/**
 * Main View
 *
 * The main view is used to start the sales POS giving the user options
 * to create a new sale or exit the application
 *
 */
@Component
public class MainView implements Viewable {

    /** Constants **/
    private static final String NEW_SALE = "1";
    private static final String EXIT = "99";

    private List<String> viewData;
    private ApplicationView nextView;
    private Scanner scannerInput;

    @Autowired
    public MainView(
            @Qualifier("mainViewTemplate") final List<String> viewData,
            final Scanner scannerInput
    ) {
        this.viewData = viewData;
        this.scannerInput = scannerInput;
    }

    public void processView() {
        viewData.forEach(System.out::println);
    }

    @Override
    public void handleUserInput() {

        final String userInput = scannerInput.nextLine();

        switch(userInput) {

            case NEW_SALE:
                this.nextView = ApplicationView.PRODUCT_ENTRY;
                break;
            case EXIT:
                this.nextView = null;
                break;
            default:
                this.nextView = ApplicationView.MAIN_MENU;
                break;

        }

    }

    @Override
    public ApplicationView getNextView() {
        return nextView;
    }
}
