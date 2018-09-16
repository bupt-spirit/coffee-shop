package coffeeshop.web.util;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@RequestScoped
public class MessageBundle {

    private static final String BUNDLE_NAME = "bundle";

    private ResourceBundle bundle;

    private static ResourceBundle getResourceBundle() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, BUNDLE_NAME);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    @PostConstruct
    private void init() {
        bundle = getResourceBundle();
    }

    public String getString(String name) {
        return bundle.getString(name);
    }

    public String getFormatted(String name, Object... objects) {
        return MessageFormat.format(
                bundle.getString(name),
                objects
        );
    }
}
