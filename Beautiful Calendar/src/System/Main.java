package System;

import DataWrapper.DataWrapper;
import Views.LoginPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {


        new LoginPage(primaryStage, new Model());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
