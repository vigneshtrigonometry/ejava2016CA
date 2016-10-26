
package appointment.resources;

import appointment.business.AppointmentBean;
import appointment.business.PeopleBean;
import appointment.tasks.AddPersonTask;
import appointment.tasks.VerifyEmailTask;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/people")
public class PeopleResource {
    
    @EJB private PeopleBean peopleBean;
    @EJB private AppointmentBean appointmentBean;
    @Resource(lookup = "concurrent/ejavaca1mes")
    private ManagedExecutorService service;
    
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public void addPeople(MultivaluedMap<String,String> form, @Suspended final AsyncResponse async)
    {
        try
        {
            AddPersonTask task = new AddPersonTask(form.getFirst("name"), form.getFirst("email"), async, peopleBean);
            service.submit(task);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @GET
    public void getAppointments(@QueryParam("email") String email, @Suspended final AsyncResponse resp)
    {
        VerifyEmailTask task = new VerifyEmailTask(email, peopleBean, resp);
        service.submit(task);
    }
    
}
