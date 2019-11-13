package DataWrapper;

import DataWrapper.DataWrapper;
import Models.Booking;
import Models.Patient;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Kenedid on 26-05-2017.
 */
public class DataWrapperTest {
    DataWrapper dataWrapper = new DataWrapper();
    @Test
    public void loginValidate() throws Exception {
        boolean output = dataWrapper.loginValidate("hem", "zeh");
        Assert.assertEquals(true,output);
    }
    @Test
    public void getSinglePatientData() throws Exception{
        Patient aBoolean =dataWrapper.getSinglePatientData("4567");
        Assert.assertNull(aBoolean);
    }

    @Test
    public void getBookingName() throws Exception{
        ArrayList<Booking> chek =dataWrapper.getBookingFromName("b");
        Assert.assertNotNull(chek);
        Assert.assertEquals(1, chek.size());
    }

}
