
package appointment.tasks;

import appointment.business.PeopleBean;
import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;


public class AddPersonTask implements Runnable {

    private String name, email;
    private AsyncResponse resp;
    private PeopleBean peopleBean;
    public AddPersonTask(String name, String email, AsyncResponse resp, PeopleBean peopleBean) {
        this.name = name;
        this.email = email;
        this.resp = resp;
        this.peopleBean = peopleBean;
    }

    
    @Override
    public void run() {
        try
        {
            peopleBean.addPeople(name, email);
            Response respon = Response.ok().build();
            resp.resume(respon);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            resp.cancel();
        }
        
    }
    
}
