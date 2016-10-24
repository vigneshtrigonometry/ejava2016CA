
package appointment.business;

import appointment.entity.Appointment;
import appointment.entity.People;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class AppointmentBean {
    
    @PersistenceContext EntityManager em;
    
    public List<Appointment> getAllAppointments(String email)
    {
        TypedQuery<People> people = em.createQuery("select p from Person p where p.email = :email", People.class);
        people.setParameter("email", email);
        People p = people.getResultList().get(0);
        TypedQuery<Appointment> appts = em.createQuery("select a from appointment a where a.people = :people",Appointment.class);
        appts.setParameter("people", people);
        List<Appointment> appt = appts.getResultList();
        return appt;
    }
    
}
