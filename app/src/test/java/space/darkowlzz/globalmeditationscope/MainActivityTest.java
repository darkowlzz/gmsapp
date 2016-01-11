package space.darkowlzz.globalmeditationscope;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

    @Test
    public void convertTimeZone() {
        DateTime givenTime = new DateTime(2015, 12, 10, 9, 15); // 7:45 PM Asia/Calcutta
        DateTime expectedTime = new DateTime(2015, 12, 10, 19, 45);
        DateTime convertedTime = MainActivity.convertTimeZone(givenTime.toLocalDateTime(), "EST", "Asia/Calcutta");
        assertThat(convertedTime, is(expectedTime));
    }
}