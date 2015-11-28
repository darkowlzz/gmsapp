package space.darkowlzz.globalmeditationscope;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import space.darkowlzz.globalmeditationscope.MainActivity.Category;

/**
 * Created by sunny on 24/11/15.
 */
public class MediEvent {
    String title;
    String description;
    String hostName;
    String periHandle;
    String twitterHandle;
    Category category;
    Integer year, month, day, hour,minute;
    boolean favorite = false;
    Integer eventID;

    MediEvent(String title, String description, String hostName, String periHandle,
              String twitterHandle, Category category, Integer ID) {
        this.title = title;
        this.description = description;
        this.hostName = hostName;
        this.periHandle = periHandle;
        this.twitterHandle = twitterHandle;
        this.category = category;
        this.eventID = ID;
    }

    public void setTime(DateTime date) {
        this.year = date.getYear();
        this.month = date.getMonthOfYear();
        this.day = date.getDayOfMonth();
        this.hour = date.getHourOfDay();
        this.minute = date.getMinuteOfHour();
    }

    public DateTime getDateObj() {
        return new DateTime(year, month, day, hour, minute);
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM yyyy");
        return dtf.print(getDateObj());
    }

    public String getTime() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
        return dtf.print(getDateObj());
    }

    public String getPeriUri() {
        return "https://www.periscope.tv/" + periHandle;
    }

    public String getTwitterUri() {
        return "https://twitter.com/" + twitterHandle;
    }
}