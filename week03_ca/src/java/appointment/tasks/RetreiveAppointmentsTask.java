/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointment.tasks;

import appointment.business.AppointmentBean;
import appointment.entity.Appointment;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author ajit kamath
 */
public class RetreiveAppointmentsTask implements Runnable{

    private AppointmentBean appointmentBean;
    private String email;
    private AsyncResponse resp;

    public RetreiveAppointmentsTask(String email, AsyncResponse resp, AppointmentBean appointmentBean) {
        this.appointmentBean = appointmentBean;
        this.email = email;
        this.resp = resp;
    }
    
    @Override
    public void run() {
                List<Appointment> appts = appointmentBean.getAllAppointments(email);
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(Appointment a:appts)
        {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();
            objBuilder.add("appointmentId", a.getAppt_id()).add("dateTime", a.getAppt_date().toString())
                    .add("description", a.getDescription()).add("personId", a.getPeople().getPid());
            arrBuilder.add(objBuilder);
        }
        resp.resume(Response.ok(arrBuilder.build()).build());
    }
    
}
