/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.business;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import logistics.entity.Delivery;
import logistics.entity.Pod;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
 *
 * @author urquhart
 */
@Stateless
public class DeliveryBean {

    @PersistenceContext
    EntityManager em;
    @Resource
    TimerService timerService;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void AddDelivery(String name, String address, String phone) {

        try {
            Delivery del = new Delivery();
            del.setName(name);
            del.setAddress(address);
            del.setPhone(phone);
            del.setCreateDate(new Date());
            Pod p = new Pod();
            p.setPkgId(del);
            p.setDeliveryDate(new Date());
            em.persist(del);
            em.persist(p);

        } catch (ConstraintViolationException ex) {
            System.out.println(ex.getConstraintViolations().toString());
        } catch (Exception e) {

            System.out.println("Error in DeliveryBean");
            e.printStackTrace();
        }

    }

    //gets the list of items from the delivery table and returns it as a list
    public List<Delivery> getDeliveryItems() {
        try {
            return em.createNamedQuery("Delivery.findAll", Delivery.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Schedule(minute = "*/5")
    public void getAcknowledgement() {
        try {
            List<Pod> pods = em.createNamedQuery("Pod.findAll", Pod.class).getResultList();
            for (Pod p : pods) {
                if (p.getAckId() == null || p.getAckId() == "") {
                    Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

                    BodyPart img = new BodyPart(p.getImage(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
                    img.setContentDisposition(FormDataContentDisposition.name("image").fileName("image.jepg").build());

                    MultiPart formData = new FormDataMultiPart()
                            .field("teamId", "40b66c20", MediaType.TEXT_PLAIN_TYPE)
                            .field("podId", p.getPodId().toString(), MediaType.TEXT_PLAIN_TYPE)
                            .field("callback", "http://10.10.25.20:8080/week5ca/callback", MediaType.TEXT_PLAIN_TYPE)
                            .field("note", p.getNote(), MediaType.TEXT_PLAIN_TYPE)
                            .bodyPart(img);
                    formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

                    WebTarget target = client.target("http://10.10.0.50:8080/epod/upload");
                    Invocation.Builder inv = target.request();
                    Response callresp = inv.post(Entity.entity(formData, formData.getMediaType()));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
