package Views;

import Controllers.BookingPageController;
import Models.Patient;
import System.GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by hamza on 03-05-2017.
 */
public class BookingPage extends GUI
{
    private BookingPageController bookingPageController;

    protected TextArea noteArea = new TextArea();
    protected TextField nameField = new TextField();
    protected TextField cprField = new TextField();
    protected TextField phoneField = new TextField();
    protected TextField emailField = new TextField();
    protected Text failInfo = new Text();

    protected DatePicker fromDate;
    protected DatePicker toDate;

    protected ComboBox hourBoxFrom;
    protected ComboBox hourBoxTo;

    protected GridPane centerPane;
    protected GridPane bottomPane;
    protected GridPane topPane;
    private FlowPane flowPane;

    protected Button addBtn;
    protected Button backBtn;
    protected Button removeBtn;
    protected Button getDataBtn;

    protected Text title;
    private Text from;
    private Text to;
    private Text note;
    private Text info;

    protected BookingPage(){super();}
    protected BookingPage(BookingPageController bookingPageController)
    {
        super();
        this.bookingPageController = bookingPageController;
    }

    //setting the fields of the page up
    protected void setBooking(Stage stage) {
        flowPane = new FlowPane();

        backBtn = getBackButton();
        addBtn = new Button("Tilføj tid");
        addBtn.setStyle("-fx-background-color: #0be000");
        addBtn.setDisable(false);
        removeBtn = new Button("Fjern tid");
        removeBtn.setStyle("-fx-background-color: #e00200");
        getDataBtn = new Button("Hent data");
        getDataBtn.setStyle("-fx-background-color: #47b5b9");

        fromDate = new DatePicker();
        toDate = new DatePicker();
        fromDate.setShowWeekNumbers(true);
        toDate.setShowWeekNumbers(true);

        from = new Text("Fra:");
        to = new Text("Til:");
        note = new Text("Note:");
        info = new Text("Personlig oplysning");
        title = new Text("Tilføj tid");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        title.setFill(Color.WHITE);

        noteArea.setPrefColumnCount(5);
        noteArea.setPrefRowCount(3);
        //It make sure that the text jumps down when its in the end
        noteArea.setWrapText(true);
        setTopPane();
        setCenterPane();
        setBottomPane();
        actionForBooking(stage);
    }

    private void setTopPane()
    {
        //Top
        topPane = new GridPane();
        topPane.setStyle("-fx-background-color: #034699;");
        topPane.setHgap(30);
        topPane.setVgap(20);
        topPane.setPadding(new Insets(10, 10, 10, 10));
        topPane.add(backBtn, 2, 2);
        topPane.add(title, 2, 1);
    }

    private void setCenterPane()
    {
        //Center
        centerPane = new GridPane();
        centerPane.setStyle("-fx-background-color: #ffffff");
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());

        ObservableList<String> hourOptions = FXCollections.observableArrayList(getHourSchedules());
        hourBoxFrom = new ComboBox(hourOptions);
        hourBoxFrom.setValue("08:00");
        hourBoxFrom.setStyle("-fx-background-color: #47b5b9");
        hourBoxTo = new ComboBox(hourOptions);
        hourBoxTo.setValue("08:00");
        hourBoxTo.setStyle("-fx-background-color: #47b5b9");

        nameField.setPromptText("Navn");
        cprField.setPromptText("CPR");
        phoneField.setPromptText("Telefon Nr.");
        emailField.setPromptText("E-mail");

        centerPane.setVgap(20);
        centerPane.setHgap(30);
        centerPane.add(info, 2, 1);
        centerPane.add(from, 1, 1);
        flowPane.getChildren().addAll(cprField, getDataBtn);
        centerPane.add(flowPane, 2, 2);
        centerPane.add(nameField, 2, 3);
        centerPane.add(fromDate, 1, 2);
        centerPane.add(phoneField, 2, 4);
        centerPane.add(hourBoxFrom, 1, 3);
        centerPane.add(to, 1, 4);
        centerPane.add(toDate, 1, 5);
        centerPane.add(hourBoxTo, 1, 6);
        centerPane.add(emailField, 2, 6);
        centerPane.add(note, 1, 7);
        centerPane.add(noteArea, 1, 8);

        addTextLimiter(cprField, 10);
        addTextLimiter(phoneField, 8);
    }

    private void setBottomPane()
    {
        //Bottom
        bottomPane = new GridPane();
        bottomPane.setStyle("-fx-background-color: #ffffff");

        bottomPane.setHgap(30);
        bottomPane.setVgap(20);
        bottomPane.setPadding(new Insets(10, 10, 10, 10));

        bottomPane.add(addBtn, 3, 0);
        bottomPane.add(failInfo, 4, 0);
    }

    private void actionForBooking(Stage stage)
    {
        //action center
        cprField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    cprField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        phoneField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Action BottomPane
        backBtn.setOnAction(event -> {
            mainPage(stage);
        });

        addBtn.setOnAction(event -> {
            saveData();
        });

        setPage(topPane, centerPane, bottomPane, stage);

        getDataBtn.setOnAction(event -> {
            try {
                if(!cprField.getText().isEmpty())
                {
                    Patient patient = bookingPageController.getPatient(cprField.getText());

                    if (patient.getName().isEmpty())
                    {
                        failInfo.setFill(Color.FIREBRICK);
                        failInfo.setText("Patienten blev ikke fundet");
                    }
                    else
                    {
                        nameField.setText(patient.getName());
                        phoneField.setText(Integer.toString(patient.getPhone()));
                        emailField.setText(patient.getEmail());
                        failInfo.setFill(Color.BLACK);
                        failInfo.setText("Patienten blev fundet");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    //returns list of hours in a day for combobox to choose from
    private ArrayList<String> getHourSchedules()
    {
        ArrayList<String> hourList = new ArrayList<>();
        for (int i = 0; i < 24; i++)
        {
            String tmpHour;
            if(i < 10) tmpHour = "0" + i;
            else tmpHour = Integer.toString(i);

            String hour = tmpHour + ":" + "00";

            hourList.add(hour);
        }
        return hourList;
    }

    //setting a maxlenght on a textfield
    private void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textField.getText().length() > maxLength) {
                    String s = textField.getText().substring(0, maxLength);
                    textField.setText(s);
                }
            }
        });
    }

    //Før hed den getData, Den henter alt data fra fields og derefter køre den en metode i controllers for at gemme de hentede felter
    private void saveData()
    {
        if (!nameField.getText().isEmpty() && !cprField.getText().isEmpty() && !phoneField.getText().isEmpty() && !emailField.getText().isEmpty())
        {
            //Vi skal kun have 10 char i cpr
            if (cprField.getText().length() == 10 && phoneField.getText().length() == 8)
            {
                //Sørge for at man ikke tilføje den samme patient flere gange
                if(!bookingPageController.checkCpr(cprField.getText()))
                {
                    bookingPageController.addPatient(cprField.getText(), nameField.getText(), phoneField.getText(), emailField.getText());
                }
                bookingPageController.addBooking(cprField.getText(), dateToString(fromDate, hourBoxFrom), noteArea.getText(), dateToString(toDate, hourBoxFrom));
                failInfo.setFill(Color.BLACK);
                failInfo.setText("Booking er blevet tilføjet");
                addBtn.setDisable(true);
                clearFields();
                addBtn.setDisable(false);
            }
            else
            {
                failInfo.setFill(Color.FIREBRICK);
                failInfo.setText("CPR nummer skal bestå af 10 cifre og telefon nummer skal bestå af 8 cifre");
            }
        }
        else
        {
            failInfo.setFill(Color.FIREBRICK);
            failInfo.setText("Husk at udfylde alle felter");
        }
    }

    private String dateToString(DatePicker date, ComboBox hour){
        String string = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String string1 = hour.getValue().toString();
        String dateHour = string + " " + string1 + ":00";
        return dateHour;
    }

    private void clearFields(){
        cprField.clear();
        nameField.clear();
        noteArea.clear();
        phoneField.clear();
        emailField.clear();
    }
}


