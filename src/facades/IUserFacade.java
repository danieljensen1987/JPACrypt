package facades;

import entities.Role;
import exceptions.NotFoundException;

public interface IUserFacade 
{
    public String login(String userName, String password) throws NotFoundException;
}
