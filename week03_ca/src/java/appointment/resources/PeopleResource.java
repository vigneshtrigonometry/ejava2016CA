
package appointment.resources;

import appointment.business.AppointmentBean;
import appointment.business.PeopleBean;
import appointment.entity.Appointment;
import appointment.tasks.AddPersonTask;
import appointment.tasks.RetreiveAppointmentsTask;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    @Produces("application/json")
    public Response getAppointments(@QueryParam("email") String email, @Suspended final AsyncResponse async)
    {
                
        if(peopleBean.findByEmail(email).isPresent())
        {
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }
    
}
