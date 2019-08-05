package com.keenan.view;


import com.keenan.enums.ApplicationView;

public interface Viewable {

    default ApplicationView renderView() {
        processView();
        handleUserInput();
        return getNextView();
    }

    void processView();
    ApplicationView getNextView();

    void handleUserInput();

}
