package space.darkowlzz.globalmeditationscope;

import java.text.SimpleDateFormat;
import java.util.Date;

import space.darkowlzz.globalmeditationscope.MainActivity.Category;

/**
 * Created by sunny on 24/11/15.
 */
public class MediEvent {
    String title;
    String description;
    String hostName;
    Category category;
    Date date = new Date();
    boolean favorite = false;
    Integer eventID;

    MediEvent(String title, String description, String hostName, Category category, Date date, Integer ID) {
        this.title = title;
        this.description = description;
        this.hostName = hostName;
        this.category = category;
        this.date = date;
        this.eventID = ID;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        return sdf.format(date);
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}