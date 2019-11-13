package Controllers;

import System.*;

import java.sql.SQLException;

/**
 * Created by Hamzah on 12-05-2017.
 */
public class BookingPageController extends Controller
{

    public BookingPageController(Model model)
    {
        super(model);
    }

    /**
     * Method for storing patients in our database.
     * @param cpr
     * @param name
     * @param phone
     * @param email
     */
    public void addPatient(String cpr, String name, String phone, String email)
    {
        try
        {
            model.addPatient(cpr, name, phone, email);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /***
     *  Method for storing bookings in our database.
     * @param cpr
     * @param dateFrom
     * @param note
     * @param dateTo
     */
    public void addBooking(String cpr, String dateFrom, String note, String dateTo)
    {
        try
        {
            model.addBooking(cpr, dateFrom, note, dateTo);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /***
     * Checks if an cpr-number exist in our db.
     * @param cpr
     * @param dateFrom
     * @param note
     * @param dateTo
     */
    public boolean checkCpr(String cpr)
    {
        try
        {
            for (int i = 0; i < model.getCprList().size(); i++)
            {

                System.out.println("for loop");
                if (cpr.equals(model.getCprList().get(i)))
                {
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
