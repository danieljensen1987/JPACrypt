package TestModel;

public class SimpleUser implements User
{
    private String username;
    private String password;
    private Role role;

    public SimpleUser(String username, String password, Role role)
    {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public Role getRole()
    {
        return role;
    }
    
}
