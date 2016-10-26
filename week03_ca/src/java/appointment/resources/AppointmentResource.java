/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointment.resources;

import appointment.business.AppointmentBean;
import appointment.entity.Appointment;
import appointment.tasks.RetreiveAppointmentsTask;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

@Path("appointment/{email}")
public class AppointmentResource {
    
    @Resource(lookup = "concurrent/ejavaca1mes")
    private ManagedExecutorService service;
    
    @EJB private AppointmentBean appointmentBean;
     
     
    @GET
    @Produces("application/json")
    public void getAppointments(@PathParam("email") String email, @Suspended final AsyncResponse resp)
    {
        RetreiveAppointmentsTask task = new RetreiveAppointmentsTask(email, resp, appointmentBean);
        service.submit(task);
    }
    
}
