/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.business;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import logistics.entity.Pod;

/**
 *
 * @author Vignesh
 */
@Stateless
public class PODBean {
    
    @PersistenceContext private EntityManager em;
    
    public List<Pod> getAllPods()
    {
        return em.createNamedQuery("Pod.findAll", Pod.class).getResultList();
    }
    
    public void update(int podId, Date d, String note, byte[] image)
    {
        Pod p = em.find(Pod.class, podId);
        p.setDeliveryDate(d);
        p.setImage(image);
        p.setNote(note);
        em.merge(p);
    }
    
    public void update (int podId, String ackId){
        Pod p = em.find(Pod.class, podId);
        p.setAckId(ackId);
        em.merge(p);
        
    }
    
}
