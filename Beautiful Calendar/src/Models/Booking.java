package Models;

/**
 * Created by Johnny on 12-05-2017.
 */
public class Booking
{
    private int bookingID;
    private Patient patient; //aggregation..
    private String fromDate;
    private String fromHour;
    private String toDate;
    private String toHour;
    private String note;

    public Booking() {}

    public int getBookingID()
    {
        return bookingID;
    }
    public Patient getPatient()
    {
        return patient;
    }
    public String getFromDate()
    {
        return fromDate;
    }
    public String getFromHour()
    {
        return fromHour;
    }
    public String getToDate()
    {
        return toDate;
    }
    public String getToHour()
    {
        return toHour;
    }
    public String getNote()
    {
        return note;
    }

    public void setBookingID(int bookingID)
    {
        this.bookingID = bookingID;
    }
    public void setPatient(Patient patient)
    {
        this.patient = patient;
    }
    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }
    public void setFromHour(String fromHour)
    {
        this.fromHour = fromHour;
    }
    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }
    public void setToHour(String toHour)
    {
        this.toHour = toHour;
    }
    public void setNote(String note)
    {
        this.note = note;
    }

}
