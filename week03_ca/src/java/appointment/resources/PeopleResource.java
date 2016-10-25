
package appointment.resources;

import appointment.business.AppointmentBean;
import appointment.business.PeopleBean;
import appointment.entity.Appointment;
import appointment.tasks.AddPersonTask;
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
    public Response getAppointments(@QueryParam("email") String email)
    {
        List<Appointment> appts = appointmentBean.getAllAppointments(email);
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(Appointment a:appts)
        {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();
            objBuilder.add("appointmentId", a.getAppt_id()).add("dateTime", a.getAppt_date().toString())
                    .add("description", a.getDescription()).add("personId", a.getPeople().getPid());
            arrBuilder.add(objBuilder);
        }
        return Response.ok(arrBuilder.build()).build();
    }
    
}
