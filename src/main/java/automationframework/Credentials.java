package automationframework;
/**
 * @author Shubham Jain
 *
 */
public class Credentials
{
    private String email = null;
    private String password = null;

    public Credentials(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }
}
