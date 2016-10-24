
package appointment.resources;

import appointment.business.AppointmentBean;
import appointment.business.PeopleBean;
import appointment.entity.Appointment;
import java.util.List;
import javax.ejb.EJB;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/people")
public class PeopleResource {
    
    @EJB private PeopleBean peopleBean;
    @EJB private AppointmentBean appointmentBean;
   
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addPeople(MultivaluedMap<String,String> form)
    {
        try
        {
            System.out.println("Reached !!!!!!!!!!!!!!!!!!!");
            peopleBean.addPeople(form.getFirst("name"), form.getFirst("email"));
            return Response.ok().build();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return Response.serverError().build();
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
