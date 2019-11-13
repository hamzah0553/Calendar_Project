package Controllers;

import Models.Booking;
import Models.Patient;
import System.Model;
import System.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Hamzah on 12-05-2017.
 */
public class CalendarPageController extends Controller
{

    public CalendarPageController(Model model)
    {
        super(model);
    }

    /**
     *  Get a list of bookings in certain week
     * @param weekNumber
     */
    public ArrayList<Booking> getBookingsForThatWeek(int weekNumber)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, weekNumber);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String betweenFromDate = sdf.format(cal.getTime());

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.add(Calendar.DATE, + 1);

        String betweenToDate = sdf.format(cal.getTime());

        ResultSet list = model.getPatientBookingBetweenDate(betweenFromDate, betweenToDate);

        ArrayList<Booking> bookings = new ArrayList<>();

        try
        {
            while (list.next())
            {
                Booking booking = new Booking();

                /** booking data */
                //used to display date
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                //from time
                Date fromDate = list.getTimestamp("booking_from_datetime");

                //to time
                Date toDate = list.getTimestamp("booking_to_datetime");

                booking.setFromDate(dateFormat.format(new Date(fromDate.getTime())));
                booking.setToDate(dateFormat.format(new Date(toDate.getTime())));
                booking.setBookingID(list.getInt("booking_id"));

                bookings.add(booking);

            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return bookings;

    }

}
