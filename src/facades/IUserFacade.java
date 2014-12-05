package facades;

import entities.User;
import exceptions.AlreadyExcistException;
import exceptions.RoleNotFoundException;
import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;

public interface IUserFacade 
{
    public String login(String userName, String password) 
            throws UserNotFoundException, WrongPasswordException;
    public String findUser(String userName)
            throws UserNotFoundException;
    public boolean addUser(String userName, String password, String role) 
            throws RoleNotFoundException,AlreadyExcistException;
    public boolean changePassword(String userName, String currentPassword, String newPassword) 
            throws UserNotFoundException, WrongPasswordException;
    public boolean deleteUser(String userName) 
            throws UserNotFoundException;
}
