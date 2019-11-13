package System;

import Controllers.CalendarPageController;
import Controllers.SearchPageController;
import Models.Booking;
import Models.Patient;
import Views.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Johnny on 12-05-2017.
 */
public class GUI
{
    //sub classes
    protected SearchPage searchPage;
    protected BookingPage bookingPage;
    private CalenderPage calenderPage;

    //controllers
    protected Controller controller;

    //mutual data among the subclass to use for editing/deleting
    protected static Booking chosenBooking;
    protected static Patient chosenPatient;

    //scene
    private Scene scene;

    public GUI(){}

    public GUI(Controller controller)
    {
        this.controller = controller;
    }

    /**
     * Sets the overall page.
     * @param top
     * @param center
     * @param bottom
     * @param stage
     */
    protected void setPage(Node top, Node center, Node bottom, Stage stage)
    {
        //set layout
        BorderPane layout = new BorderPane();
        layout.setTop(top);
        layout.setCenter(center);
        layout.setBottom(bottom);

        StackPane root = new StackPane();
        if(!root.getChildren().contains(layout)) {
            root.getChildren().addAll(layout);
        }

        //view only has one scene
        if(scene == null) {
            scene = new Scene(root, 1000, 700, Color.WHITE);
        }

        /* show stage with scene within */
        stage.setTitle("Tandlæge kalender booking");
        stage.setMinHeight(700);
        stage.setMinWidth(1000);
        stage.setScene(scene);
        stage.show();
    }

    /** Opens calendar page (known as mainpage) */
    public void mainPage(Stage stage)
    {
        if(calenderPage == null)
        {
            calenderPage = new CalenderPage(new CalendarPageController(new Model()));
            System.out.println("calendar page created");
        }
        calenderPage.setCalendarPage(stage);

        /** if GUI is closed, log out */
        stage.setOnCloseRequest(event ->
        {
            stage.close();

            Stage newStage = new Stage();
            new LoginPage(newStage, new Model());
        });
    }

    /** Creates 'searchBar' field and return it */
    protected TextField getSearchBar()
    {
        TextField searchBar = new TextField();
        searchBar.setPrefWidth(400);
        searchBar.setPrefHeight(45);
        searchBar.setPromptText("Søg...");

        return searchBar;
    }

    /**
     * Back button in the left corner.
     * @return
     */
    protected Button getBackButton()
    {
        Image backLogo = new Image("/Pictures/back35.png");
        Button backButton = new Button();
        backButton.setGraphic(new ImageView(backLogo));
        backButton.setStyle("-fx-base: #e78500;");
        return backButton;
    }

    /**
     * Forward button.
     * @return
     */
    protected Button getForwardButton()
    {
        Image forLogo = new Image("/Pictures/for35.png");
        Button forwardButton = new Button();
        forwardButton.setGraphic(new ImageView(forLogo));
        forwardButton.setStyle("-fx-base: #e78500;");
        return forwardButton;
    }

    /**
     * Search page page, when searching.
     * @return
     */
    protected SearchPage getSearchPage()
    {
        if(searchPage == null)
        {
            searchPage = new SearchPage(new SearchPageController(new Model()));
        }
        return searchPage;
    }
}
