package coffeeshop.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DateTimeFormater {
    
    @Inject
    MessageBundle bundle;

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(bundle.getString("DateTime.Pattern"));
        dateFormat.setTimeZone(TimeZone.getTimeZone(bundle.getString("DateTime.TimeZone")));
        return dateFormat.format(date);
    }
}
