
package appointment.tasks;

import appointment.business.PeopleBean;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

public class VerifyEmailTask implements Runnable {
    
    private PeopleBean peopleBean;
    private String email;
    private AsyncResponse resp;
    
    public VerifyEmailTask(String email, PeopleBean peopleBean, AsyncResponse resp)
    {
        this.peopleBean = peopleBean;
        this. email = email;
        this.resp = resp;
    }

    @Override
    public void run() {
       if(peopleBean.findByEmail(email) != null)
        {
            resp.resume(Response.ok().build());
        }
        resp.resume(Response.status(Response.Status.NOT_FOUND).build());

    }
}
