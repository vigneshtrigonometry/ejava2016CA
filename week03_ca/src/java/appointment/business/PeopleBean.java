
package appointment.business;

import appointment.entity.People;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PeopleBean {
    
    @PersistenceContext EntityManager em;
    
    public void addPeople(String name, String email)
    {
        People p = new People();
        p.setName(name);
        p.setEmail(email);
        em.persist(p);
    }
}
