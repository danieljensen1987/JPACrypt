package facades;

import exceptions.AlreadyExcistException;
import exceptions.RoleNotFoundException;
import exceptions.SameException;
import exceptions.UserNotFoundException;
import exceptions.WrongOldPasswordException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    public void setUp()
    {
        facade = UserFacadeDB.getFacade(true);
        emf = Persistence.createEntityManagerFactory("JPAcryptPU");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown()
    {
        em.getTransaction().begin();
        em.createNativeQuery("delete from Users").executeUpdate();
        em.createNativeQuery("insert into Users(id, password, role) values ('admin@test.com','test','admin')").executeUpdate();
        em.createNativeQuery("insert into Users(id, password, role) values ('student@test.com','test','student')").executeUpdate();
        em.createNativeQuery("insert into Users(id, password, role) values ('teacher@test.com','test','teacher')").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testLogin() throws UserNotFoundException, RoleNotFoundException, AlreadyExcistException
    {
        facade.addUser("test@test.com", "test", "admin");
        String expected = facade.login("test@test.com", "test");
        String actual = "admin";
        assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void testLoginFail() throws UserNotFoundException
    {
        facade.login("fail", "fail");
    }

    @Test
    public void testAddUser() throws RoleNotFoundException, AlreadyExcistException
    {
        boolean expected = facade.addUser("teacher", "test", "teacher");
        assertTrue(expected);
    }

    @Test(expected = RoleNotFoundException.class)
    public void testAddUserWrongRole() throws RoleNotFoundException, AlreadyExcistException
    {
        facade.addUser("test", "test", "role");
    }
    
    @Test
    public void testChangePassword() throws SameException, UserNotFoundException, WrongOldPasswordException
    {
        boolean expected = facade.changePassword("admin@test.com", "changed", "notchanged");
        assertTrue(expected);
    }
    
    @Test
    public void testDeleteUser() throws UserNotFoundException, RoleNotFoundException, AlreadyExcistException{
        facade.addUser("delete@me.com", "test", "student");
        boolean expected = facade.deleteUser("delete@me.com");
        assertTrue(expected);
    }
}
