
package appointment.business;

import appointment.entity.People;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
