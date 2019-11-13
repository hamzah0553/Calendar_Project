package Controllers;

import Models.Booking;
import System.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Johnny on 12-05-2017.
 */
public class SearchPageController extends Controller
{
    public SearchPageController(Model model)
    {
        super(model);
    }

    /**
     * Returns a list of patients specified by  the search value.
     * @param searchValue
     * @return
     */
    public ArrayList<Booking> getPatientBookingList(String searchValue)
    {
        if(isInteger(searchValue))
        {
            return model.getBookingFromCPR(searchValue);
        }
        else
        {
            return model.getBookingFromName(searchValue);
        }
    }

    /** Method that checks if string is integer */
    private boolean isInteger(String str)
    {
        String regex = "\\d+";
        if(str.matches(regex)) return true;
        return false;
    }
}
