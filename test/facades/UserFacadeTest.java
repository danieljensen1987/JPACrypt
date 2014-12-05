package facades;

import exceptions.AlreadyExcistException;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import utilities.CloseableManager;

public class UserFacadeTest
{

    UserFacadeDB facade;
    static EntityManagerFactory emf;
    EntityManager em;

    public UserFacadeTest()
    {
    }

    @Before
    public void setUp()
    {
        facade = UserFacadeDB.getFacade(true);
        emf = Persistence.createEntityManagerFactory("JPAcryptPU");
        em = emf.createEntityManager();

        try (CloseableManager cm = new CloseableManager(emf)) {
            cm.getTransaction().begin();
            cm.createNativeQuery("delete from Users").executeUpdate();
            cm.createNativeQuery("insert into Users(id, password, rolle) values ('admin@test.com','test','admin')").executeUpdate();
            cm.createNativeQuery("insert into Users(id, password, rolle) values ('student@test.com','test','student')").executeUpdate();
            cm.createNativeQuery("insert into Users(id, password, rolle) values ('teacher@test.com','test','teacher')").executeUpdate();
            cm.getTransaction().commit();
        }
    }

    @AfterClass
    public static void tearDown()
    {
        try (CloseableManager cm = new CloseableManager(emf)) {
            cm.getTransaction().begin();
            cm.createNativeQuery("delete from Users").executeUpdate();
            cm.getTransaction().commit();
        }
    }

    @Test
    public void testLogin() throws UserNotFoundException, WrongPasswordException
    {
        String expected = facade.login("admin@test.com", "test");
        String actual = "admin";
        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testLoginFail() throws UserNotFoundException, WrongPasswordException
    {
        facade.login("fail", "fail");
    }

    @Test
    public void testAddUser() throws AlreadyExcistException
    {
        boolean expected = facade.addUser("teacher", "test", "teacher");
        assertTrue(expected);
    }

    @Test
    public void testChangePassword() throws UserNotFoundException, WrongPasswordException
    {
        boolean expected = facade.changePassword("teacher@test.com", "tt");
        assertTrue(expected);
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException, AlreadyExcistException
    {
        facade.addUser("delete@me.com", "test", "student");
        boolean expected = facade.deleteUser("delete@me.com");
        assertTrue(expected);
    }
}
