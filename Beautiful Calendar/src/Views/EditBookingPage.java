package Views;

import Controllers.EditBookingPageController;
import Models.Booking;
import Models.Patient;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;

/**
 * Created by hamza on 09-05-2017.
 */
public class EditBookingPage extends BookingPage {

    private Button editBtn;
    private Text editTitle = new Text("Redigere tid");
    private Button backButton;
    private String lastSearch;

    private EditBookingPageController editBookingPageController;

    protected EditBookingPage(EditBookingPageController editBookingPageController)
    {
        super();
        this.editBookingPageController = editBookingPageController;
    }

    /**
     * Sets the booking page layout.
     * @param text
     * @param stage
     */
    protected void setEditBookingPage(String text, Stage stage){
        super.setBooking(stage);

        editTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        editTitle.setFill(Color.WHITE);

        cprField.setDisable(true);
        getDataBtn.setDisable(true);

        editBtn = new Button("Rediger");
        editBtn.setStyle("-fx-background-color: #0be000");
        backButton = getBackButton();
        bottomPane.getChildren().remove(addBtn);
        bottomPane.add(editBtn, 3, 0);
        bottomPane.add(removeBtn, 2, 0);

        centerPane.getChildren().remove(getDataBtn);

        topPane.getChildren().remove(title);
        topPane.getChildren().remove(super.backBtn);
        topPane.add(editTitle,2,1);
        topPane.add(backButton, 2, 2);

        //action
        backButton.setOnAction(event ->
        {
            getSearchPage().setSearchPage(text, stage);
        });

        editBtn.setOnAction(event ->
        {
            if(chosenBooking != null && chosenPatient != null)
            {
                editedBooking(chosenBooking);
                editedPatient(chosenPatient);
                editBookingPageController.updateData(chosenBooking, chosenPatient);
                failInfo.setText("Ændringerne er blevet gemt");
                failInfo.setFill(Color.BLACK);

            }
            else {
                failInfo.setText("FAIL");
                failInfo.setFill(Color.FIREBRICK);
            }

        });

        removeBtn.setOnAction(event -> {
            try {

                editBookingPageController.removeBooking(chosenBooking);
                failInfo.setText("Bookingen er blevet fjernet");
            } catch (Exception e)
            {
                failInfo.setFill(Color.FIREBRICK);
                failInfo.setText("Teknisk fejl kald support");
            }
            getSearchPage().setSearchPage(lastSearch, stage);


        });
    }

    protected void getData (Booking booking, Patient patient, String text)
    {
        StringConverter<LocalDate> converter = editBookingPageController.converter();
        nameField.setText(patient.getName());
        cprField.setText(patient.getCpr());
        emailField.setText(patient.getEmail());
        phoneField.setText(Integer.toString(patient.getPhone()));

        noteArea.setText(booking.getNote());
        hourBoxFrom.setValue(booking.getFromHour());
        hourBoxTo.setValue(booking.getToHour());
        String string = booking.getFromDate().substring(0, 10);
        String string1 = booking.getToDate().substring(0, 10);
        fromDate.setValue(converter.fromString(string));
        toDate.setValue(converter.fromString(string1));
        lastSearch = text;
    }

    private void editedBooking(Booking booking)
    {
        booking.setNote(noteArea.getText());
        StringConverter convertDate = editBookingPageController.converter();
        booking.setFromDate(convertDate.toString(fromDate.getValue()));
        booking.setToDate(convertDate.toString(toDate.getValue()));
        booking.setFromHour(hourBoxFrom.getValue().toString());
        booking.setToHour(hourBoxFrom.getValue().toString());
        chosenBooking = booking;
    }

    private void editedPatient(Patient patient)
    {
        patient.setCpr(cprField.getText());
        patient.setName(nameField.getText());
        patient.setPhone(Integer.parseInt(cprField.getText()));
        System.out.println(patient.getPhone());
        patient.setEmail(emailField.getText());
        chosenPatient = patient;
    }

}
