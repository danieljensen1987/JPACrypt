package facades;

import com.google.gson.Gson;
import entities.Role;
import entities.User;
import exceptions.NotFoundException;
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
        if (true){
            facade = new UserFacadeDB();
        }
        return facade;
    }
    
    

    @Override
    public String login(String userName, String password) throws NotFoundException
    {
        User user = em.find(User.class, userName);
        if (user == null) {
            throw new NotFoundException("Fail");
        } else {
            if (!user.getPassword().equals(password)) {
                throw new NotFoundException("Fail");
            }
            return "" + user.getRole();
        }
    }

    public void createTestData() throws NotFoundException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
