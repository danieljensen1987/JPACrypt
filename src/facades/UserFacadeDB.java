package facades;

import com.google.gson.Gson;
import entities.Role;
import entities.User;
import exceptions.AlreadyExcistException;
import exceptions.RoleNotFoundException;
import exceptions.SameException;
import exceptions.UserNotFoundException;
import exceptions.WrongOldPasswordException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserFacadeDB implements IUserFacade
{

    private static UserFacadeDB facade = new UserFacadeDB();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAcryptPU");
    EntityManager em = emf.createEntityManager();
    private final Gson gson = new Gson();

    public static UserFacadeDB getFacade(boolean b)
    {
        if (true) {
            facade = new UserFacadeDB();
        }
        return facade;
    }

    @Override
    public String login(String userName, String password) throws UserNotFoundException
    {
        User user = em.find(User.class, userName);
        if (user == null) {
            throw new UserNotFoundException("Fail");
        } else {
            if (!user.getPassword().equals(password)) {
                throw new UserNotFoundException("Fail");
            }
            return "" + user.getRole();
        }
    }

    @Override
    public boolean addUser(String userName, String password, String role) throws RoleNotFoundException, AlreadyExcistException
    {
        boolean add = false;
        Role r = em.find(Role.class, role);
        if (r == null) {
            throw new RoleNotFoundException("Fail");
        } else {
            User user = em.find(User.class, userName);
            if (user != null) {
                throw new AlreadyExcistException("Fail");
            } else {
                user = new User(userName, password, r);
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
                add = true;
            }
        }
        return add;
    }

    @Override
    public boolean changePassword(String userName, String newPassword, String oldPassword) throws UserNotFoundException, SameException, WrongOldPasswordException
    {
        boolean change = false;
        User user = em.find(User.class, userName);
        if (user == null) {
            throw new UserNotFoundException("No User with given username");
        }
        if (user.getPassword().equals(newPassword)) {
            throw new SameException("Same Password");
        } 
        if(oldPassword != user.getPassword()){
            throw new WrongOldPasswordException("Wrong Password");
        }else {
            user.setPassword(newPassword);
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            change = true;
        }
        return change;
    }

    @Override
    public boolean deleteUser(String userName) throws UserNotFoundException
    {
        boolean delete = false;
        User user = em.find(User.class, userName);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        } else {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            delete = true;
        }
        return delete;
    }

}
