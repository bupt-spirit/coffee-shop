package coffeeshop.web.admin;

import coffeeshop.ejb.InitManager;
import coffeeshop.ejb.UserManagerException;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class InitController {
    
    @EJB
    private InitManager initManager;
        
    public void insertDemoData() throws IOException, URISyntaxException, UserManagerException {
        initManager.insertDemoData();
    }
}
