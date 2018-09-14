package coffeeshop.web.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

@RequestScoped
public class MessageBundle {
    
    private static final String BUNDLE_NAME = "bundle";
    
    private ResourceBundle bundle;

    public ResourceBundle getBundle() {
        return bundle;
    }
    
    public static ResourceBundle getResourceBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, BUNDLE_NAME);
    }
    
    @PostConstruct
    private void init() {
        bundle = getResourceBundle();
    }
    
    public String getString(String name) {
        return bundle.getString(name);
    }
    
    public String getFormated(String name, Object... objs) {
        return MessageFormat.format(
                bundle.getString(name),
                objs
        );
    }
}
