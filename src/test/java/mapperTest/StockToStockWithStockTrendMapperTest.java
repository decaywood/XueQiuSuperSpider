package mapperTest;

import org.decaywood.entity.Stock;
import org.decaywood.mapper.stockFirst.StockToStockWithStockTrendMapper;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 20:05
 */
public class StockToStockWithStockTrendMapperTest {

    @Test
    public void testFunc() throws RemoteException {
        StockToStockWithStockTrendMapper mapper = new StockToStockWithStockTrendMapper();
        List<Stock> stocks = TestCaseGenerator.generateStocks();
        boolean res = stocks.stream().map(mapper.andThen(Stock::getStockTrend)).noneMatch(x -> x.getHistory().isEmpty());
        Assert.assertTrue(res);
    }
}
