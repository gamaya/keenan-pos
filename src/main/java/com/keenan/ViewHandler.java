package com.keenan;

import com.keenan.enums.ApplicationView;
import com.keenan.view.Viewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ViewHandler {

    /** List of Views Available to our Application **/
    private final Map<ApplicationView, Viewable> viewMap;

    @Autowired
    public ViewHandler(final Map<ApplicationView,Viewable> viewMap) {
        this.viewMap = viewMap;
    }

    public void handleViews() {

        // Initialize Application with the Main Menu
        Viewable currentView = viewMap.get(ApplicationView.MAIN_MENU);

        // While a current view is available, continue running application
        while(currentView != null) {

            clearScreen();
            ApplicationView nextView = currentView.renderView();
            currentView = viewMap.get(nextView);

        }

    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
