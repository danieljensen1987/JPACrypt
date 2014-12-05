package facades;

import com.google.gson.Gson;
import entities.Role;
import entities.User;
import exceptions.AlreadyExcistException;
import exceptions.RoleNotFoundException;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utilities.CloseableManager;

public class UserFacadeDB implements IUserFacade
{

    private static UserFacadeDB facade = new UserFacadeDB();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAcryptPU");
    EntityManager em = emf.createEntityManager();

    public static UserFacadeDB getFacade(boolean b)
    {
        if (true) {
            facade = new UserFacadeDB();
        }
        return facade;
    }

    @Override
    public String login(String userName, String password) throws UserNotFoundException, WrongPasswordException
    {
        try (CloseableManager cm = new CloseableManager(emf)) {
            User user = cm.find(User.class, userName);
            if (user == null) {
                throw new UserNotFoundException("Invalid Username or Password");
            } else {
                if (!user.getPassword().equals(password)) {
                    throw new WrongPasswordException("Invalid Username or Password ");
                }
                return "" + user.getRole();
            }
        }
    }
    
    @Override
    public String findUser(String userName)throws UserNotFoundException
    {
        try (CloseableManager cm = new CloseableManager(emf)) {
            User user = cm.find(User.class, userName);
            if (user == null){
                throw new UserNotFoundException("No Users with that username");
            }
            Gson gson = new Gson();
            return gson.toJson(user);
        }
    }

    @Override
    public boolean addUser(String userName, String password, String role) throws RoleNotFoundException, AlreadyExcistException
    {
        try (CloseableManager cm = new CloseableManager(emf)) {
            boolean add = false;
            Role r = cm.find(Role.class, role);
            if (r == null) {
                throw new RoleNotFoundException("Wrong Role");
            } else {
                User user = cm.find(User.class, userName);
                if (user != null) {
                    throw new AlreadyExcistException("Fail");
                } else {
                    user = new User(userName, password, role);
                    cm.getTransaction().begin();
                    cm.persist(user);
                    cm.getTransaction().commit();
                    add = true;
                }
            }
            return add;
        }
    }

    @Override
    public boolean changePassword(String userName, String currentPassword, String newPassword) 
            throws UserNotFoundException, WrongPasswordException
    {
        try (CloseableManager cm = new CloseableManager(emf)) {
            boolean change = false;
            User user = cm.find(User.class, userName);
            if (user == null) {
                throw new UserNotFoundException("No User with given username");
            }
            if (!user.getPassword().equals(currentPassword)) {
                throw new WrongPasswordException("Wrong Password");
            } else {
                user.setPassword(newPassword);
                cm.getTransaction().begin();
                cm.merge(user);
                cm.getTransaction().commit();
                change = true;
            }
            return change;
        }
    }

    @Override
    public boolean deleteUser(String userName) throws UserNotFoundException
    {
        try (CloseableManager cm = new CloseableManager(emf)) {
            boolean delete = false;
            User user = cm.find(User.class, userName);
            if (user == null) {
                throw new UserNotFoundException("User not found");
            } else {
                cm.getTransaction().begin();
                cm.remove(user);
                cm.getTransaction().commit();
                delete = true;
            }
            return delete;
        }
    }

}
