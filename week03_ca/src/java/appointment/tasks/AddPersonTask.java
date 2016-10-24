
package appointment.tasks;

import appointment.business.PeopleBean;
import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;


public class AddPersonTask implements Runnable {

    private String name, email;
    private AsyncContext ctx;
    @EJB PeopleBean peopleBean;
    public AddPersonTask(String name, String email, AsyncContext ctx) {
        this.name = name;
        this.email = email;
        this.ctx = ctx;
    }

    
    @Override
    public void run() {
        try
        {
            peopleBean.addPeople(name, email);
            Response resp = Response.ok().build();
            ctx.complete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ctx.complete();
        }
        
    }
    
}
