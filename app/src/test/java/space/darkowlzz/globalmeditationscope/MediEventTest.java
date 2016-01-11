package space.darkowlzz.globalmeditationscope;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sunny on 10/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class MediEventTest {

    ArrayList<String> data = new ArrayList<>();

    @Before
    public void setupData() {
        data.add("99"); // eventID
        data.add("10"); // day of month
        data.add("9:15 AM"); // time of day
        data.add("Shane"); // event host name
        data.add("@shane"); // periscope handle
        data.add("blah blah blah"); // meditation description
        data.add("Move"); // meditation category
    }

    @Test
    public void readDataAndCreateMediEventObject() {
        MediEvent me = new MediEvent();
        me.saveData(data);

        assertThat(me.eventID, is(99));
        assertThat(me.year, is(MainActivity.GMS_YEAR));
        assertThat(me.month, is(MainActivity.GMS_MONTH));
        assertThat(me.day, is(10));
        assertThat(me.hour, is(9));
        assertThat(me.minute, is(15));
        assertThat(me.hostName, is("Shane"));
        assertThat(me.periHandle, is("shane"));
        assertThat(me.twitterHandle, is("shane"));
        assertThat(me.description, is("blah blah blah"));
        assertThat(me.category, is(MainActivity.Category.MOVE));
    }

    @Test
    public void setTimeMediEvent() {
        MediEvent me = new MediEvent();
        me.saveData(data);

        DateTime aTime = new DateTime(2015, 12, 10, 19, 45);
        me.setTime(aTime);
        assertThat(me.year, is(MainActivity.GMS_YEAR));
        assertThat(me.month, is(MainActivity.GMS_MONTH));
        assertThat(me.day, is(10));
        assertThat(me.hour, is(19));
        assertThat(me.minute, is(45));
    }
}
