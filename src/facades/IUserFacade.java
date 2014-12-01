package facades;

import exceptions.AlreadyExcistException;
import exceptions.RoleNotFoundException;
import exceptions.SameException;
import exceptions.UserNotFoundException;
import exceptions.WrongOldPasswordException;

public interface IUserFacade 
{
    public String login(String userName, String password) throws UserNotFoundException;
    public boolean addUser(String userName, String password, String role) 
            throws RoleNotFoundException,AlreadyExcistException;
    public boolean changePassword(String userName, String newPassword, String oldPassword) 
            throws UserNotFoundException, SameException, WrongOldPasswordException;
    public boolean deleteUser(String userName) throws UserNotFoundException;
}
