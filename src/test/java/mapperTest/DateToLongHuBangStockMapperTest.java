package mapperTest;

import org.decaywood.entity.Stock;
import org.decaywood.mapper.dateFirst.DateToLongHuBangStockMapper;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/27 11:12
 */
public class DateToLongHuBangStockMapperTest {

    @Test
    public void testFunction() throws RemoteException {
        DateToLongHuBangStockMapper mapper = new DateToLongHuBangStockMapper(null);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.NOVEMBER, 26);
        List<Stock> stocks =  mapper.apply(calendar.getTime());
        Assert.assertFalse(stocks.isEmpty());
    }

    @Test
    public void testNoExchangeDate() throws RemoteException {

        DateToLongHuBangStockMapper mapper = new DateToLongHuBangStockMapper(null);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.NOVEMBER, 21);
        List<Stock> stocks =  mapper.apply(calendar.getTime());
        Assert.assertTrue(stocks.isEmpty());


    }


}
