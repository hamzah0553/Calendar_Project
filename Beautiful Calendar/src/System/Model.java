package System;

import DataWrapper.DataWrapper;
import Database.Database;
import Models.Booking;
import Models.Patient;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hamzah on 12-05-2017.
 */
public class Model extends DataWrapper
{

    public Model()
    {
        super();
    }

    /**
     *
     * @param cpr
     * @return
     */
    public ArrayList<Booking> getBookingFromCPR(String cpr)
    {
        try
        {
            return super.getBookingFromCPR(cpr);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Booking> getBookingFromName(String name)
    {
        try
        {
            return super.getBookingFromName(name);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * TODO: Denne skal returnere resultset i stedet for arraylist
     * @param fromDateTime
     * @param toDateTime
     * @return
     */
    public ResultSet getPatientBookingBetweenDate(String fromDateTime, String toDateTime)
    {
        try
        {
            return super.getBookingsBetweenDates(fromDateTime, toDateTime);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update patient data + booking.
     * @param booking
     * @param patient
     */
    public void updateEverything(Booking booking, Patient patient)
    {
        try
        {
            super.updateEverything(booking, patient);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Delete booking.
     * @param booking
     */
    public void removeBooking(Booking booking)
    {
        try
        {
            super.removeBooking(booking);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
