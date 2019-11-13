package Views;

import System.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Kenedid on 26-05-2017.
 */
public class LoginPage
{
    private Button submit = new Button("Login");
    private TextField userField = new TextField();
    private PasswordField passField = new PasswordField();
    private Scene loginScene;
    private GridPane pane = new GridPane();
    private Text text = new Text("Brugernavn");
    private Text text2 = new Text("Adgangskode");
    private Text error = new Text();

    private Model model;

    /**
     * Create stage and display login prompt.
     * @param primaryStage
     * @param model
     */
    public LoginPage(Stage primaryStage, Model model)
    {
        this.model = model;

        pane.setStyle("-fx-background-color: #f3ffe1");
        settingLogin();
        primaryStage.setTitle("Log ind");
        primaryStage.setScene(loginScene);
        submit.setStyle("-fx-background-color: #2ae039");

        submit.setOnAction(Event ->
        {
            loginButtonPressed(primaryStage);
        });

        userField.setOnKeyPressed(KeyEvent ->
        {
            if(KeyEvent.getCode() == KeyCode.ENTER) loginButtonPressed(primaryStage);
        });

        passField.setOnKeyPressed(KeyEvent ->
        {
            if(KeyEvent.getCode() == KeyCode.ENTER) loginButtonPressed(primaryStage);
        });

        primaryStage.show();
    }

    /**
     * Login button pressed.
     * @param primaryStage
     */
    private void loginButtonPressed(Stage primaryStage)
    {
        try {
            checkLogin(userField.getText(), passField.getText(), primaryStage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if login is correct, checking model.
     * @param username
     * @param password
     * @param primaryStage
     * @throws SQLException
     */
    private void checkLogin(String username, String password, Stage primaryStage) throws SQLException {

        if (model.loginValidate(username, password)) {
            startSystem(primaryStage);
        } else {
            {
                error.setText("Forkert login");
                error.setFill(Color.FIREBRICK);
            }
        }
    }

    /**
     * Login prompt.
     */
    private void settingLogin(){

        userField.setPromptText("Brugernavn");

        passField.setPromptText("Kodeord");

        pane.setHgap(30);
        pane.setVgap(20);
        pane.setPadding(new Insets(10, 10, 10, 10));

        pane.add(submit, 0, 4);
        pane.add(userField, 0, 1);
        pane.add(passField, 0, 3);
        pane.add(text, 0, 0);
        pane.add(text2, 0, 2);
        pane.add(error, 0, 5);
        pane.setAlignment(Pos.CENTER);

        if(loginScene == null) {
            loginScene = new Scene(pane, 600, 400, Color.WHITE);
        }

    }

    /**
     * Starting system, gui etc.
     * @param primaryStage
     */
    private void startSystem(Stage primaryStage)
    {
        Model model = new Model();

        Controller controller = new Controller(model);
        GUI gui = new GUI(controller);
        gui.mainPage(primaryStage);
    }

}
