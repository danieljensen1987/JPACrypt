package facades;

import exceptions.AlreadyExcistException;
import exceptions.RoleNotFoundException;
import exceptions.SameException;
import exceptions.UserNotFoundException;

public interface IUserFacade 
{
    public String login(String userName, String password) throws UserNotFoundException;
    public boolean addUser(String userName, String password, String role) 
            throws RoleNotFoundException,AlreadyExcistException;
    public boolean changePassword(String userName, String password) 
            throws UserNotFoundException, SameException;
    public boolean deleteUser(String userName) throws UserNotFoundException;
}
