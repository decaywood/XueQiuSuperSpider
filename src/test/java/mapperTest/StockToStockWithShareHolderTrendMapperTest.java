package mapperTest;

import org.decaywood.entity.Stock;
import org.decaywood.entity.trend.Trend;
import org.decaywood.mapper.stockFirst.StockToStockWithShareHolderTrendMapper;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/30 10:33
 */
public class StockToStockWithShareHolderTrendMapperTest {

    @Test
    public void testFunc() throws RemoteException {

        List<Stock> stocks = TestCaseGenerator.generateStocks();
        StockToStockWithShareHolderTrendMapper mapper = new StockToStockWithShareHolderTrendMapper();

        boolean b = stocks.stream()
                .map(mapper.andThen(Stock::getShareHoldersTrend).andThen(Trend::getHistory))
                .noneMatch(List::isEmpty);

        Assert.assertTrue(b);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongArgument() throws RemoteException {

        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.AUGUST, 5);
        Date since = calendar.getTime();

        calendar.set(2015, Calendar.MAY, 28);
        Date until = calendar.getTime();

        StockToStockWithShareHolderTrendMapper mapper =
                new StockToStockWithShareHolderTrendMapper(until, since);

    }

    @Test
    public void testRangeFunc() throws RemoteException {
        List<Stock> stocks = TestCaseGenerator.generateStocks();
        StockToStockWithShareHolderTrendMapper mapper = new StockToStockWithShareHolderTrendMapper();

        int count1 = stocks.stream()
                .map(mapper.andThen(Stock::getShareHoldersTrend).andThen(Trend::getHistory))
                .mapToInt(List::size).reduce(Integer::sum).getAsInt();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.AUGUST, 5);
        Date since = calendar.getTime();

        calendar.set(2015, Calendar.MAY, 28);
        Date until = calendar.getTime();

        StockToStockWithShareHolderTrendMapper rangeMapper =
                null;
        try {
            rangeMapper = new StockToStockWithShareHolderTrendMapper(since, until);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        int count2 = stocks.stream()
                .map(rangeMapper.andThen(Stock::getShareHoldersTrend).andThen(Trend::getHistory))
                .mapToInt(List::size).reduce(Integer::sum).getAsInt();

        Assert.assertTrue(count2 < count1);

    }



}
