
package appointment.business;

import appointment.entity.People;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class PeopleBean {
    
    @PersistenceContext EntityManager em;
    
    public void addPeople(String name, String email)
    {
        People p = new People();
        String unique = (String) UUID.randomUUID().toString().subSequence(0, 8);
        p.setPid(unique);
        p.setName(name);
        p.setEmail(email);
        em.persist(p);
    }
    
    public People findByEmail(String email)
    {
        try{
        TypedQuery<People> query = em.createQuery("select p from People p where p.email = :email", People.class);
        query.setParameter("email", email);
        List<People> pList = query.getResultList();
        return pList.isEmpty()? null:pList.get(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
            
        }
       
    }
}
