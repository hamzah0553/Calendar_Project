package System;

import Models.Booking;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Kenedid on 25-05-2017.
 */
public class ModelTest {
    Model model = new Model();
   @Test
    public void getBookingFromCPR() throws Exception {
        ArrayList<Booking> check = new ArrayList<>();
        ArrayList<Booking> output = model.getBookingFromCPR("2222222222");
        Assert.assertNotEquals(check,output);

    }
}