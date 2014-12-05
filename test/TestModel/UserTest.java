package TestModel;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class UserTest
{

    private User user;

    @Before
    public void setUp()
    {
        user = new SimpleUser("aa", "test", "admin");
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
    
    @Test
    public void getRole(){
        assertThat(user.getRole(), is("admin"));
    }
}
