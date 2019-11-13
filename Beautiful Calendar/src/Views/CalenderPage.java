package Views;

import Controllers.BookingPageController;
import Controllers.CalendarPageController;
import Models.Booking;
import System.GUI;
import System.Model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by hamza on 09-05-2017.
 */
public class CalenderPage extends GUI
{
    //top
    private GridPane topGridPane;

    //center
    private GridPane centerGridPane;

    //controller
    private CalendarPageController calendarPageController;

    //week number
    private int weekNumber;

    public CalenderPage(CalendarPageController calendarPageController)
    {
        super();
        this.calendarPageController = calendarPageController;
        this.weekNumber = getWeekNr();
    }

    /**
     * Creates the calender page.
     * @param stage
     */
    public void setCalendarPage(Stage stage)
    {
        /** top */
        Text title = new Text("Tandlæge booking");
        title.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        title.setFill(Color.WHITE);

        TextField searchBar = getSearchBar();

        Button bookingButton = new Button("Opret tid");
        bookingButton.setStyle("-fx-background-color: #47b5b9");
        Button backButton = getBackButton();
        Button forwardButton = getForwardButton();

        // actions
        searchBar.setOnKeyPressed(keyEvent ->
        {
            if(keyEvent.getCode() == KeyCode.ENTER)
            {
                if(!searchBar.getText().isEmpty())
                {
                    searchPage = getSearchPage();
                    searchPage.setSearchPage(searchBar.getText(), stage);
                }
                else
                {
                    searchBar.setStyle("-fx-border-color: #992316; -fx-border-width: 3; -fx-border-insets: 2");
                }
            }
        });

        searchBar.setOnMousePressed(event -> {
            searchBar.selectAll();  //needed for when clicking after focus
        });

        /** center */
        centerGridPane = createCalender();
        centerGridPane.setStyle("-fx-background-color: #ffffff");
        String weekString = String.valueOf(weekNumber);
        Label label = new Label("Uge: " + weekString);
        centerGridPane.add(label, 0, 0);

        /** pane setting */
        HBox topRightButtonBox = new HBox();
        topRightButtonBox.getChildren().addAll(bookingButton, backButton, forwardButton);
        topRightButtonBox.setSpacing(15);
        topRightButtonBox.setAlignment(Pos.CENTER_RIGHT);

        topGridPane = new GridPane();
        topGridPane.setHgap(30);
        topGridPane.setVgap(20);
        topGridPane.setPadding(new Insets(10, 10, 10, 10));
        topGridPane.add(title, 2, 1);
        topGridPane.add(searchBar, 2, 2);
        topGridPane.add(topRightButtonBox, 10, 2);
        topGridPane.setStyle("-fx-background-color: #034699;");

        setPage(topGridPane, centerGridPane, null, stage);

        /** actions */
        bookingButton.setOnAction(event ->
        {
            if (bookingPage == null)
            {
                bookingPage = new BookingPage(new BookingPageController(new Model()));
            }
            bookingPage.setBooking(stage);
        });

        backButton.setOnAction(event -> {
            //TODO: go back 1 week
            weekNumber = weekNumber - 1;
            centerGridPane = createCalender();
            centerGridPane.add(label, 0, 0);
        });

        forwardButton.setOnAction(event -> {
            //TODO: go forward 1 week
            weekNumber = weekNumber + 1;
            centerGridPane = createCalender();
            centerGridPane.add(label, 0, 0);
        });
    }

    /**
     * The calender skema.
     * @return
     */
    private GridPane createCalender(){
        //TODO: Dele metoden op i flere dele
        GridPane calendarGridPane;
        Text[] weekDays;
        Text[] hours;
        Button[][] btn;

        hours = new Text[11];
        weekDays = new Text[7];
        btn = new Button[7][11];

        calendarGridPane = new GridPane();

        weekDays[0] = new Text("Mandag");
        weekDays[1] = new Text("Tirsdag");
        weekDays[2] = new Text("Onsdag");
        weekDays[3] = new Text("Torsdag");
        weekDays[4] = new Text("Fredag");
        weekDays[5] = new Text("Lørdag");
        weekDays[6] = new Text("Søndag");

        hours[0] = new Text("08:00");
        hours[1] = new Text("09:00");
        hours[2] = new Text("10:00");
        hours[3] = new Text("11:00");
        hours[4] = new Text("12:00");
        hours[5] = new Text("13:00");
        hours[6] = new Text("14:00");
        hours[7] = new Text("15:00");
        hours[8] = new Text("16:00");
        hours[9] = new Text("17:00");
        hours[10] = new Text("18:00");

        calendarGridPane.setHgap(40);
        calendarGridPane.setVgap(40);
        calendarGridPane.setAlignment(Pos.TOP_CENTER);
        calendarGridPane.setPadding(new Insets(10));


        for (int i = 0; i < 7; i++)
        {
            weekDays[i].setFont(Font.font("Tahoma", FontWeight.MEDIUM, 20));
            calendarGridPane.add(weekDays[i], i+1, 0);
            calendarGridPane.add(hours[i], 0, i+1);
        }

        int length = btn.length;

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
       // System.out.println(sdf.format(cal.getTime()));

        String date = sdf.format(cal.getTime());

        //has an list of bookings for that week..

        ArrayList<Booking> bookings = calendarPageController.getBookingsForThatWeek(weekNumber);

        for (int i = 0; i < length; i++)
        {

            //if i = 0 it is monday, do not add +1 day.
            if(i > 0)
            {

                cal.add(Calendar.DATE, 1);
                date = sdf.format(cal.getTime());

            }

            for (int j = 0; j < length; j++)
            {

                String bookingFrom = date + " " + hours[j].getText();

                String bookingTo = date + " " + hours[j+1].getText();

                for(Booking s : bookings)
                {

                    //System.out.println(s.getFromDate());

                    if(s.getFromDate().compareTo(bookingFrom) >= 0 && bookingTo.compareTo(s.getFromDate()) >= 0)
                    {

                        btn[i][j] = new Button(s.getFromDate());
                        btn[i][j].setPrefSize(80, 30);
                        calendarGridPane.add(btn[i][j], 1+i, 1 + j);
                        btn[i][j].setStyle("-fx-background-color: #47b5b9");

                    }

                }

            }

        }

        return calendarGridPane;

    }

    //calculate and return week number
    private int getWeekNr()
    {
        //week converter
        LocalDate date = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = date.get(woy);
        return weekNumber;
    }
}
