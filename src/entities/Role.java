package entities;

import java.io.Serializable; 
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")})
public class Role implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ROLENAME")
    private String rolename;
    @OneToMany(mappedBy = "role")
    private Collection<User> userCollection;

    public Role()
    {
    }

    public Role(String rolename)
    {
        this.rolename = rolename;
    }

    public String getRolename()
    {
        return rolename;
    }

    public void setRolename(String rolename)
    {
        this.rolename = rolename;
    }

    public Collection<User> getUserCollection()
    {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection)
    {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (rolename != null ? rolename.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        return !((this.rolename == null && other.rolename != null) || (this.rolename != null && !this.rolename.equals(other.rolename)));
    }

    @Override
    public String toString()
    {
        return rolename;
    }
    
}
