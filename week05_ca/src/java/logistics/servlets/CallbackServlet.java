/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logistics.business.PODBean;

/**
 *
 * @author Vignesh
 */
@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
    
    @EJB
    private PODBean podBean;
            
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
    {
        String podId = req.getParameter("podId");
        String ackId = req.getParameter("ackId");
        
        try{
            podBean.update(Integer.parseInt(podId), ackId);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("text/html");
            req.getRequestDispatcher("/faces/deliveryandpods.xhtml?faces-redirect=true").forward(req, resp);
        } catch(Exception e){
            e.printStackTrace();
        }
        
        
        
    }
    
}
