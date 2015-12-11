package collectTest;

import org.decaywood.collector.DateRangeCollector;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/27 10:17
 */
public class DateRangeCollectorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument() throws RemoteException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.SEPTEMBER, 21);
        Date from = calendar.getTime();
        calendar.set(2016, Calendar.OCTOBER, 24);
        Date to = calendar.getTime();

        new DateRangeCollector(to, from);
    }


    @Test
    public void functionTest() throws RemoteException {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2015, Calendar.SEPTEMBER, 21);
        Date from = calendar.getTime();

        calendar.set(2015, Calendar.OCTOBER, 21);
        Date to = calendar.getTime();
        List<Date> strings = new DateRangeCollector(from, to).get();

        Assert.assertTrue(strings.size() == 31);

    }

}
