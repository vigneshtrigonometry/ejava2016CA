/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.rest;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import logistics.business.DeliveryBean;
import logistics.business.PODBean;
import logistics.entity.Delivery;
import logistics.entity.Pod;

/**
 *
 * @author Vignesh
 */
@RequestScoped
@Path("/items")
public class DeliveryItemResource {

    @EJB
    private DeliveryBean mgr;
    @EJB
    private PODBean pmgr;

    @GET
    @Produces("application/json")
    public Response getItems() {
        try {
            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

            List<Pod> pods = pmgr.getAllPods();
            for (Pod p : pods) {
                JsonObjectBuilder objBuilder = Json.createObjectBuilder();
                objBuilder.add("teamId", "40b66c20");
                objBuilder.add("podId", p.getPodId());
                objBuilder.add("name", p.getPkg().getName());
                objBuilder.add("address", p.getPkg().getAddress());
                objBuilder.add("name", p.getPkg().getPhone());
                arrBuilder.add(objBuilder);
            }
            return Response.ok(arrBuilder.build()).build();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

    }
}
