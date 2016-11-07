/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.servlets;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import logistics.business.DeliveryBean;
import logistics.business.PODBean;

/**
 *
 * @author Vignesh
 */
@WebServlet("/upload")
@MultipartConfig
public class GetUploadServlet extends HttpServlet {

    @EJB private PODBean mgr; 
    @EJB private DeliveryBean dmgr;
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String podId = new String(readPart(req.getPart("podId")));
        String note = new String(readPart(req.getPart("note")));
        byte[] file = readPart(req.getPart("image"));
        String time = new String(readPart(req.getPart("time")));
        Date d = new Date(Long.parseLong(time));
        mgr.update(Integer.parseInt(podId), d, note, file);
        dmgr.getAcknowledgement();
    }
     private byte[] readPart(Part p) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int sz = 0;
        try (InputStream is = p.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                while (-1 != (sz = bis.read(buffer))) {
                    baos.write(buffer, 0, sz);
                }
                buffer = baos.toByteArray();
            }
        }
        return (buffer);
    }

}
