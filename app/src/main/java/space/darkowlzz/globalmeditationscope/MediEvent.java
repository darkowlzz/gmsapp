package space.darkowlzz.globalmeditationscope;

import android.text.format.Time;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.TimeZone;

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
    Integer year, month, day, hour, minute;
    boolean favorite = false;
    Integer eventID;

    MediEvent(Integer ID, String title, String description, String hostName, String periHandle,
              String twitterHandle, Category category) {
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

    public DateTime getLocalDateObj() {
        String timezone = TimeZone.getDefault().getID();
        return MainActivity.convertTimeZone(getDateObj().toLocalDateTime(), "EST", timezone);
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM yyyy");
        return dtf.print(getLocalDateObj());
    }

    public String getTime() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("hh:mm a");
        return dtf.print(getLocalDateObj());
    }

    public String getPeriUri() {
        return "https://www.periscope.tv/" + periHandle;
    }

    public String getTwitterUri() {
        return "https://twitter.com/" + twitterHandle;
    }
}