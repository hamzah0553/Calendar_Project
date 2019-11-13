package Controllers;

import Models.Booking;
import Models.Patient;
import System.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Johnny on 12-05-2017.
 */
public class EditBookingPageController extends Controller
{
    public EditBookingPageController(Model model)
    {
        super(model);
    }

    /**
     * Updates patient data and booking data.
     * @param booking
     * @param patient
     */
    public void updateData(Booking booking, Patient patient)
    {
        model.updateEverything(booking, patient);
    }

    /**
     * Deletes an given booking
     * @param booking
     */
    public void removeBooking (Booking booking) { model.removeBooking(booking); }

    public StringConverter converter()
    {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>()
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dtf.format(date);
                } else {
                    return null;
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dtf);
                } else {
                    return null;
                }
            }
        };
        return converter;
    }

}
