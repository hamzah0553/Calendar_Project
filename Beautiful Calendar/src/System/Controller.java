package System;

import Models.Patient;

import java.sql.SQLException;

/**
 * Created by Hamzah on 12-05-2017.
 */
public class Controller
{
    //for subclasses to use model
    protected Model model;

    public Controller(Model model)
    {
        super();
        this.model = model;
    }

    /**
     * get single patient by cpr nr.
     * used by bookingPageController & searchPageController
     */
    public Patient getPatient(String cpr)
    {
        try
        {
            return model.getSinglePatientData(cpr);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
