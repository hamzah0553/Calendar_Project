package Views;

import Controllers.EditBookingPageController;
import Controllers.SearchPageController;
import Models.Booking;
import System.GUI;
import System.Model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by Johnny on 06-05-2017.
 */
public class SearchPage extends GUI
{
    private EditBookingPage editBookingPage;
    private SearchPageController searchPageController;

    /**searchPage components*/
    //top
    private TextField searchBar;
    private Text title;
    private Button backButton;

    //center
    private TableView tableView;

    //bottom
    private Button editButton;

    public SearchPage(SearchPageController searchPageController)
    {
        super();
        this.searchPageController = searchPageController;
    }

    protected void setSearchPage(String text, Stage stage)
    {
        /** top */
        searchBar = getSearchBar();
        searchBar.setText(text);

        title = new Text("Søgeresultater");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        title.setFill(Color.WHITE);
        backButton = getBackButton();

        /** center */
        tableView = getBookingsTable(text);
        tableView.setPlaceholder(new Label("Ingen booking tider fundet"));

        /** bottom */
        editButton = new Button("Rediger");
        editButton.setStyle("-fx-background-color: #47b5b9");
        editButton.setDisable(true);

        /**set actions*/
        setActionOnComponents(text, stage);

        /** pane setting */
        GridPane gridPane = new GridPane();
        gridPane.setHgap(30);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(title, 2,1);
        gridPane.add(searchBar, 3, 2);
        gridPane.add(backButton, 2, 2);
        gridPane.setStyle("-fx-background-color: #034699;");

        setPage(gridPane, tableView, editButton, stage);
    }

    /** SearchPage components with actions */
    private void setActionOnComponents(String text, Stage stage)
    {
        backButton.setOnAction(event ->
        {
            mainPage(stage);
        });

        editButton.setOnAction(event ->
        {
            if (editBookingPage == null) editBookingPage = new EditBookingPage(new EditBookingPageController(new Model()));
            editBookingPage.setEditBookingPage(text, stage);

            if(chosenBooking != null && chosenPatient != null)
            {
                editBookingPage.getData(chosenBooking, chosenPatient, searchBar.getText());
            }
        });

        //skriver i søgefeltet
        searchBar.setOnKeyPressed(keyEvent ->
        {

            //trykker enter
            if(keyEvent.getCode() == KeyCode.ENTER)
            {
                if(!searchBar.getText().isEmpty())
                {
                    //åben søge vinduet.
                    getSearchPage().setSearchPage(searchBar.getText(), stage);
                }
                else
                {
                    searchBar.setStyle("-fx-border-color: #992316; -fx-border-width: 3; -fx-border-insets: 2");
                }
            }
        });

        //tabeller af resultater...
        tableView.setOnMouseClicked(event ->
        {
            if(event.getClickCount() == 1)
            {
                chosenBooking = (Booking)tableView.getSelectionModel().getSelectedItem();
                chosenPatient = searchPageController.getPatient(chosenBooking.getPatient().getCpr());

                editButton.setDisable(false);
            }
        });
    }

    /** Creates TableView with some patient data and some booking data */
    private TableView getBookingsTable(String text)
    {
        TableView table = new TableView();
        table.setStyle("-fx-background-color: #ffffff");
        table.setEditable(false);

        TableColumn cprCol = new TableColumn("CPR nr.");
        cprCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Booking, Integer>, ObservableValue>() {
            @Override
            public ObservableValue call(CellDataFeatures<Booking, Integer> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getPatient().getCpr());
            }
        });
        cprCol.setMinWidth(50);

        TableColumn nameCol = new TableColumn("Fulde navn");
        nameCol.setCellValueFactory(new Callback<CellDataFeatures<Booking, String>, ObservableValue>() {
            public ObservableValue call(CellDataFeatures<Booking, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getPatient().getName());
            }
        });
        nameCol.setMinWidth(80);

        TableColumn fromDateCol = new TableColumn("Fra dato");
        fromDateCol.setCellValueFactory(new Callback<CellDataFeatures<Booking, String>, ObservableValue>() {
            public ObservableValue call(CellDataFeatures<Booking, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getFromDate().substring(0, 10));
            }
        });
        fromDateCol.setPrefWidth(30);

        TableColumn fromHourCol = new TableColumn("Fra kl.");
        fromHourCol.setCellValueFactory(new Callback<CellDataFeatures<Booking, String>, ObservableValue>() {
            public ObservableValue call(CellDataFeatures<Booking, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getFromHour());
            }
        });
        fromHourCol.setPrefWidth(10);

        TableColumn toDateCol = new TableColumn("Til dato");
        toDateCol.setCellValueFactory(new Callback<CellDataFeatures<Booking, String>, ObservableValue>() {
            public ObservableValue call(CellDataFeatures<Booking, String> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getToDate().substring(0, 10));
            }
        });
        toDateCol.setPrefWidth(30);

        TableColumn toHourCol = new TableColumn("Til kl.");
        toHourCol.setCellValueFactory(new Callback<CellDataFeatures<Booking, String>, ObservableValue>() {
            public ObservableValue call(CellDataFeatures<Booking, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getToHour());
            }
        });
        toHourCol.setPrefWidth(10);

        TableColumn bookNote = new TableColumn("Note");
        bookNote.setCellValueFactory(new Callback<CellDataFeatures<Booking, String>, ObservableValue>() {
            public ObservableValue call(CellDataFeatures<Booking, String> param) {
                return new ReadOnlyObjectWrapper(param.getValue().getNote());
            }
        });
        bookNote.setMinWidth(100);

        //create observable list
        ObservableList<Booking> bookingList = FXCollections.observableArrayList(searchPageController.getPatientBookingList(text));

        //set items and columns
        table.setItems(bookingList);
        table.getColumns().addAll(cprCol, nameCol, fromDateCol, fromHourCol, toDateCol, toHourCol, bookNote);

        // setting column size to fit depending on column name
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

}
