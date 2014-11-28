package TestModel;

public class RoleMockUp implements Role
{
    String role;
    public RoleMockUp(String role){
        this.role = role;
    }

    @Override
    public String toString()
    {
        return role;
    }
    
    
}
