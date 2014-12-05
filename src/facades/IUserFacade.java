package facades;

import exceptions.AlreadyExcistException;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;

public interface IUserFacade 
{
    public String login(String userName, String password) 
            throws UserNotFoundException, WrongPasswordException;
    public String findUser(String userName)
            throws UserNotFoundException;
    public boolean addUser(String userName, String password, String role) 
            throws AlreadyExcistException;
    public boolean changePassword(String userName, String newPassword) 
            throws UserNotFoundException;
    public boolean deleteUser(String userName) 
            throws UserNotFoundException;
}
