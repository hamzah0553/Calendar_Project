package Models;

/**
 * Created by Johnny on 12-05-2017.
 */
public class Patient
{
    /** cpr changed to string due to integer limit (approx 2,1 billion) */
    private String cpr;
    private String name;
    private int phone;
    private String email;

    //used for creating patient data
    public Patient(String cpr, String name, int phone, String email)
    {
        this.cpr = cpr;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getCpr()
    {
        return cpr;
    }
    public String getName()
    {
        return name;
    }
    public int getPhone()
    {
        return phone;
    }
    public String getEmail()
    {
        return email;
    }

    public void setCpr(String cpr)
    {
        this.cpr = cpr;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setPhone(int phone)
    {
        this.phone = phone;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
}
