package space.darkowlzz.globalmeditationscope.model;

import android.text.format.Time;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import space.darkowlzz.globalmeditationscope.MainActivity;
import space.darkowlzz.globalmeditationscope.MainActivity.Category;
import space.darkowlzz.gsheets2a.GSheets2A;

/**
 * Created by sunny on 24/11/15.
 */
public class MediEvent implements GSheets2A.DataObject {
    public String title;
    public String description;
    public String hostName;
    public String periHandle;
    public String twitterHandle;
    public Category category;
    public Integer year, month, day, hour, minute;
    public boolean favorite = false;
    public Integer eventID;

    /*
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
    */

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
        //DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM yyyy");
        //return dtf.print(getLocalDateObj());
        return getDate(getLocalDateObj());
    }

    public String getDate(DateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMMM yyyy");
        return dtf.print(dateTime);
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

    protected DateTime getDateTime(Integer dateOfMonth, String timeOfDay) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("hh:mm a");
        DateTime time = dateTimeFormatter.parseDateTime(timeOfDay);
        time = new DateTime(MainActivity.GMS_YEAR, MainActivity.GMS_MONTH, dateOfMonth, time.getHourOfDay(), time.getMinuteOfHour());
        return time;
    }

    @Override
    public void saveData(ArrayList<String> data) {
        this.eventID = Math.round(Float.parseFloat(data.get(0)));
        this.title = "Unknown";
        this.description = data.get(5);
        this.hostName = data.get(3);
        this.periHandle = data.get(4);
        if (this.periHandle.startsWith("@")) {
            this.periHandle = this.periHandle.substring(1);
        }
        this.twitterHandle = this.periHandle;
        this.category = MainActivity.getCategory(data.get(6));

        Integer dateOfMonth = Math.round(Float.parseFloat(data.get(1)));
        String timeOfDay = data.get(2);
        DateTime time = getDateTime(dateOfMonth, timeOfDay);
        setTime(time);
    }
}