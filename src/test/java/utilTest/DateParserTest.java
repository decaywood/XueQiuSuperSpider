package utilTest;

import org.decaywood.utils.DateParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * @author: decaywood
 * @date: 2015/11/29 22:10.
 */
public class DateParserTest {


    @Test
    public void testFunc() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.JANUARY, 31);

        Assert.assertEquals("20141231", DateParser.getTimePrefix(calendar.getTime(), true));
        Assert.assertEquals("20150131", DateParser.getTimePrefix(calendar.getTime(), false));

        calendar.set(2015, Calendar.MARCH, 31);
        Assert.assertEquals("20141231", DateParser.getTimePrefix(calendar.getTime(), true));

        calendar.set(2015, Calendar.JUNE, 30);
        Assert.assertEquals("20150331", DateParser.getTimePrefix(calendar.getTime(), true));

        calendar.set(2015, Calendar.SEPTEMBER, 30);
        Assert.assertEquals("20150630", DateParser.getTimePrefix(calendar.getTime(), true));

        calendar.set(2015, Calendar.DECEMBER, 30);
        Assert.assertEquals("20150930", DateParser.getTimePrefix(calendar.getTime(), true));
    }
}
