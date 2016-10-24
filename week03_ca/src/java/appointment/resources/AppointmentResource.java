/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointment.resources;

import appointment.business.AppointmentBean;
import appointment.entity.Appointment;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("appointment/{email}")
public class AppointmentResource {
    
    
     @EJB private AppointmentBean appointmentBean;
     
     
    @GET
    @Produces("application/json")
    public Response getAppointments(@PathParam("email") String email)
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
