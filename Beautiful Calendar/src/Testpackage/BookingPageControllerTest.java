package Testpackage;

import org.junit.Assert;
import org.junit.Test;
import Controllers.BookingPageController;
import System.Model;
import static org.junit.Assert.*;

/**
 * Created by Kenedid on 26-05-2017.
 */
public class BookingPageControllerTest {

    Model model = new Model();
    @Test
    public void checkCpr() throws Exception {
        BookingPageController bookingPageController = new BookingPageController(model);
         boolean output = bookingPageController.checkCpr("4567");
        Assert.assertEquals(true,output);
    }

}