package facades;

import exceptions.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class UserFacadeTest 
{
    UserFacadeDB facade;
    EntityManagerFactory emf;
    EntityManager em;

    public UserFacadeTest()
    {
    }
    
    @Before
    public void setUp(){
        facade = UserFacadeDB.getFacade(true);
        emf = Persistence.createEntityManagerFactory("JPAcryptPU");
        em = emf.createEntityManager();
    }
    
//    @After
//    public void tearDown(){
//        em.getTransaction().begin();
//        em.createNativeQuery("delete from Users").executeUpdate();
//        em.getTransaction().commit();
//    }
    
    @Test
    public void testLogin() throws NotFoundException{
        String expected = facade.login("admin@test.com", "test");
        String actual = "Admin";
        assertEquals(expected, actual);
    }
    
    @Test(expected = NotFoundException.class)
    public void testLoginFail() throws NotFoundException{
        facade.login("fail", "fail");
    }
    
}
