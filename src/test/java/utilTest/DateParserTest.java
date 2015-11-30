package utilTest;

import org.decaywood.utils.DateParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: decaywood
 * @date: 2015/11/29 22:10.
 */
public class DateParserTest {


    @Test
    public void testGetTimePrefixFunc() {

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

    @Test
    public void testGetDateFunc() {

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2015, Calendar.JANUARY, 31);

        Calendar calendar2 = Calendar.getInstance();
        Date date = DateParser.parseDate("20150131");
        calendar2.setTime(date);

        Assert.assertEquals(calendar1.get(Calendar.YEAR), calendar2.get(Calendar.YEAR));
        Assert.assertEquals(calendar1.get(Calendar.MONTH), calendar2.get(Calendar.MONTH));
        Assert.assertEquals(calendar1.get(Calendar.DATE), calendar2.get(Calendar.DATE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongArgumentTest1() {
        DateParser.parseDate("2015730");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongArgumentTest2() {
        DateParser.parseDate("201x730");
    }

}
