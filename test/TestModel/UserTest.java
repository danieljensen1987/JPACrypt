package TestModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class UserTest
{

    private User user;
    private Role role;

    @Before
    public void setUp()
    {
        role = new RoleMockUp();
        user = new SimpleUser("aa", "test", role);
    }

    @Test
    public void getUsername()
    {
        assertThat(user.getUsername(), is("aa"));
    }
    
    @Test
    public void getPassword()
    {
        assertThat(user.getPassword(), is("test"));
    }
}
