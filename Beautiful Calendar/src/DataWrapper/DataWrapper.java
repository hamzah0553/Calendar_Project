package DataWrapper;

import Database.Database;
import Models.Booking;
import Models.Patient;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Johnny on 09-05-2017.
 */
public class DataWrapper
{
    public DataWrapper()
    {
        super();
    }

    // SearchPage: return bookings using cpr
    public ArrayList<Booking> getBookingFromCPR(String cpr) throws SQLException
    {
        /** create statement, execute and return a resultSet */
        String sql = "SELECT p.cpr, p.name, b.booking_id, b.booking_from_datetime, b.booking_to_datetime, b.booking_note " +
                "FROM Patient p INNER JOIN Booking b ON b.cpr = ? AND p.cpr = ?";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, cpr);
        preparedStatement.setString(2, cpr);
        ResultSet rs = Database.getDatabase().returnResultSetDB(preparedStatement);

        return convertToArrayList(rs);
    }

    // SearchPage: return bookings using name
    public ArrayList<Booking> getBookingFromName(String name) throws SQLException
    {
        String sql = "SELECT p.cpr, p.name, b.booking_id, b.booking_from_datetime, b.booking_to_datetime, b.booking_note " +
                "FROM Patient p INNER JOIN Booking b ON b.cpr = p.cpr AND p.name LIKE ?;";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, name + "%");
        ResultSet rs = Database.getDatabase().returnResultSetDB(preparedStatement);

        return convertToArrayList(rs);
    }

    /** method to convert from ResultSet to ArrayList with patient & booking data */
    private ArrayList<Booking> convertToArrayList(ResultSet rs)
    {
        ArrayList<Booking> bookings = new ArrayList<>();

        if(rs == null) return null;
        else
        {
            try
            {
                while (rs.next())
                {
                    Booking booking = new Booking();
                    /** patient data */
                    String id = rs.getString("cpr");
                    String name = rs.getString("name");

                    Patient patient = new Patient(id, name, 0, null);
                    booking.setPatient(patient);

                    /** booking data */
                    //used to display date
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat hourFormat = new SimpleDateFormat("kk:mm");

                    //from time
                    Timestamp fromTimeStamp = rs.getTimestamp("booking_from_datetime");
                    Date fromDate = new Date(fromTimeStamp.getTime());

                    //to time
                    Timestamp toTimeStamp = rs.getTimestamp("booking_to_datetime");
                    Date toDate = new Date(toTimeStamp.getTime());


                    booking.setFromDate(dateFormat.format(fromDate));
                    booking.setToDate(dateFormat.format(toDate));

                    booking.setFromHour(hourFormat.format(fromDate));
                    booking.setToHour(hourFormat.format(toDate));

                    booking.setNote(rs.getString("booking_note"));

                    booking.setBookingID(rs.getInt("booking_id"));

                    bookings.add(booking);
                }

                rs.close();
                Database.getDatabase().getConnection().close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return bookings;
        }
    }

    /**
     * Return data for a single patient by cpr.
     * @param searchedCPR
     * @return
     * @throws SQLException
     */
    public Patient getSinglePatientData(String searchedCPR) throws SQLException
    {
        String cpr = "";
        String name = "";
        int phone = 0;
        String email = "";
        Patient patient;

        String sql = "SELECT * FROM Patient WHERE cpr = ?";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, searchedCPR);
        ResultSet rs = Database.getDatabase().returnResultSetDB(preparedStatement);

            while (rs.next())
            {
                cpr = rs.getString("cpr");
                name = rs.getString("name");
                phone = rs.getInt("phone");
                email = rs.getString("email");

                patient = new Patient(cpr, name, phone, email);
                return patient;
            }

        return null;
    }

    /**
     * Adding patient to our database.
     * @param cpr
     * @param name
     * @param phone
     * @param email
     * @throws SQLException
     */
    public void addPatient(String cpr, String name, String phone, String email) throws SQLException
    {
        String sql = "INSERT INTO Patient VALUE (?,?,?,?)";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, cpr);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, email);
        Database.getDatabase().executeUpdateDB(preparedStatement);
    }

    /**
     * Add booking to our database.
     * @param cpr
     * @param dateFrom
     * @param note
     * @param dateTo
     * @throws SQLException
     */
    public void addBooking(String cpr, String dateFrom, String note, String dateTo) throws SQLException
    {
        String sql = "INSERT INTO Booking(cpr, booking_from_datetime, booking_note, booking_to_datetime) Value (?,?,?,?)";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, cpr);
        preparedStatement.setTimestamp(2, stringToTS(dateFrom));
        preparedStatement.setString(3, note);
        preparedStatement.setTimestamp(4, stringToTS(dateTo));
        Database.getDatabase().executeUpdateDB(preparedStatement);
    }

    /**
     * Returning all patients stored in database.
     * @return
     * @throws SQLException
     */
    public ArrayList<String> getCprList() throws SQLException
    {
        String sql = "SELECT cpr FROM Patient";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        ResultSet rs = Database.getDatabase().returnResultSetDB(preparedStatement);

        ArrayList<String> list = new ArrayList<>();

        if (rs == null)
        {
            return null;
        }
        else
        {
            while (rs.next())
            {
                list.add(rs.getString("cpr"));
            }
        }
        return list;
    }

    /**
     * Converts a date to a timestamp.
     * @param date
     * @return
     */
    private Timestamp stringToTS(String date)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Update patient data + booking data.
     * @param booking
     * @param patient
     * @throws SQLException
     */
    public void updateEverything(Booking booking, Patient patient) throws SQLException
    {
        updateBooking(booking);
        updatePatient(patient);
    }

    /**
     * update booking data
     * @param booking
     * @throws SQLException
     */
    private void updateBooking(Booking booking) throws SQLException
    {
        String sql = "UPDATE Booking SET booking_from_datetime = ? , booking_note = ?, booking_to_datetime  = ? WHERE booking_id = ?";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        String newFromDate = booking.getFromDate();
        String newFromHour = booking.getFromHour() + ":00";
        String fromTimeStamp = newFromDate + " " + newFromHour;
        preparedStatement.setTimestamp(1, stringToTS(fromTimeStamp));

        String newToDate = booking.getToDate();
        String newToHour = booking.getToHour() + ":00";
        String toTimeStamp = newToDate + " " + newToHour;
        preparedStatement.setString(2, booking.getNote());
        preparedStatement.setTimestamp(3, stringToTS(toTimeStamp));
        preparedStatement.setInt(4, booking.getBookingID());
        Database.getDatabase().executeUpdateDB(preparedStatement);
    }

    /**
     * update patient data
     * @param patient
     * @throws SQLException
     */
    private void updatePatient(Patient patient) throws SQLException
    {
        String sql = "UPDATE Patient SET cpr = ?, `name` = ? , phone = ?, email = ? WHERE cpr = ?";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, patient.getCpr());
        preparedStatement.setString(2, patient.getName());
        preparedStatement.setInt(3, patient.getPhone());
        preparedStatement.setString(4, patient.getEmail());
        preparedStatement.setString(5, patient.getCpr());
        Database.getDatabase().executeUpdateDB(preparedStatement);
    }

    /**
     * Delete booking.
     * @param booking
     * @throws SQLException
     */
    public void removeBooking(Booking booking) throws SQLException
    {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";
        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, booking.getBookingID());
        Database.getDatabase().executeUpdateDB(preparedStatement);
    }

    /**
     * Created by Blerim 17-05-2017.
     * TODO: Denne skal returnere arraylist fremfor resultset.
     * @param fromDate
     * @param toDate
     * @return
     * @throws SQLException
     */
    protected ResultSet getBookingsBetweenDates(String fromDate, String toDate) throws SQLException
    {
        /** create statement, execute and return a resultSet */
        String sql = "SELECT * FROM Booking where booking_from_datetime >= ? "+
                "and ? >= booking_to_datetime ORDER BY booking_from_datetime ASC";

        PreparedStatement preparedStatement = Database.getDatabase().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, fromDate);
        preparedStatement.setString(2, toDate);
        return Database.getDatabase().returnResultSetDB(preparedStatement);
    }

    /**
     * Checks if login exist in our db.
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean loginValidate(String username,String password) throws SQLException {

        String sql = "SELECT * FROM user WHERE username = ? And password = ?";

        PreparedStatement prepStmt = Database.getDatabase().getConnection().prepareStatement(sql);
        prepStmt.setString(1, username);
        prepStmt.setString(2, password);
        ResultSet rs = Database.getDatabase().returnResultSetDB(prepStmt);

        int count = 0;

        while (rs.next())
        {
            count++;
        }

        if (count == 0)
        {
            return false;
        }

        return true;
    }

}

