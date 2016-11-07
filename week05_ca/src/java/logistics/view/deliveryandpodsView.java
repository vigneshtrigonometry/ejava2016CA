/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.view;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import logistics.business.DeliveryBean;
import logistics.business.PODBean;
import logistics.entity.Delivery;
import logistics.entity.Pod;

/**
 *
 * @author urquhart
 */
@RequestScoped
@Named
public class deliveryandpodsView {
    
    private List<Pod> podList;
    private List<Delivery> deliveryList;
    
    @EJB
    private DeliveryBean delBean;
    
    @EJB
    private PODBean podBean;
    
    @PostConstruct
    void init(){
        podList = new LinkedList<>();
        podList = podBean.getAllPods();
        
        deliveryList = new LinkedList<>();
        deliveryList = delBean.getDeliveryItems();
    }

    public List<Pod> getPodList() {
        return podList;
    }

    public void setPodList(List<Pod> podList) {
        this.podList = podList;
    }

    public List<Delivery> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }
    
    
}
