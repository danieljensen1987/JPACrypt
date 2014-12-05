package program;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class test 
{
    public static void main(String[] args)
    {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAcryptPU");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        
//        User admin = new User("admin@test.com", "test", new Role("Admin"));
//        User teacher = new User("teacher@test.com", "test", new Role("Teacher"));
//        User student = new User("student@test.com", "test", new Role("Student"));
//        
//        em.persist(admin);
//        em.persist(teacher);
//        em.persist(student);
//        em.getTransaction().commit();
//        
//        User find = em.find(User.class, "admin@test.com");
//        System.out.println(find);
        Role check = check("admin@test.com", "test");
        System.out.println(check.getRolename());
    }
    
    public static Role check(String userName, String password){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAcryptPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        User user = em.find(User.class, userName);
        em.close();
        
//        if (!user.getPassword().equals(password)){
            return null;
//        } else {
//            return user.getRole();
//        }
    }
}
