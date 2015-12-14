package collectTest;

import org.decaywood.collector.MarketQuotationsRankCollector;
import org.decaywood.collector.MarketQuotationsRankCollector.StockType;
import org.decaywood.entity.Stock;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 14:55
 */
public class MarketQuotationsRankCollectorTest {


    @Test(expected = IllegalArgumentException.class)
    public void testNull() throws RemoteException {

        MarketQuotationsRankCollector collector =
                new MarketQuotationsRankCollector(null,
                        MarketQuotationsRankCollector.ORDER_BY_TURNOVER_RATE,
                        -5);
        Assert.assertNotNull(collector.getStockType());
        MarketQuotationsRankCollector collector1 =
                new MarketQuotationsRankCollector(StockType.GROWTH_ENTERPRISE_BOARD,
                        null,
                        -5);
        Assert.assertNotNull(collector1.getOrderPattern());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testOverFlowTopK() throws RemoteException {
        MarketQuotationsRankCollector collector =
                new MarketQuotationsRankCollector(StockType.GROWTH_ENTERPRISE_BOARD,
                        MarketQuotationsRankCollector.ORDER_BY_TURNOVER_RATE,
                        -5);
    }


    @Test
    public void testTopKMaxSize() throws Exception {
        MarketQuotationsRankCollector collector =
                new MarketQuotationsRankCollector(StockType.GROWTH_ENTERPRISE_BOARD,
                        MarketQuotationsRankCollector.ORDER_BY_TURNOVER_RATE,
                        100);
        List<Stock> stocks = collector.get();
        Assert.assertTrue(stocks.size() <= MarketQuotationsRankCollector.TOPK_MAX_SHRESHOLD);
    }

    @Test
    public void testTopKSize() throws Exception {
        int orderSize = 3;
        MarketQuotationsRankCollector collector =
                new MarketQuotationsRankCollector(StockType.GROWTH_ENTERPRISE_BOARD,
                        MarketQuotationsRankCollector.ORDER_BY_TURNOVER_RATE,
                        orderSize);
        List<Stock> stocks = collector.get();
        Assert.assertTrue(stocks.size() == orderSize);
    }

    @Test
    public void testStockType() throws RemoteException {
        doTestStockType(StockType.GROWTH_ENTERPRISE_BOARD);
        doTestStockType(StockType.HK);
        doTestStockType(StockType.SH_A);
        doTestStockType(StockType.SH_B);
        doTestStockType(StockType.SMALL_MEDIUM_ENTERPRISE_BOARD);
        doTestStockType(StockType.SZ_A);
        doTestStockType(StockType.SZ_B);
        doTestStockType(StockType.US);
    }

    private void doTestStockType(StockType type) throws RemoteException {
        MarketQuotationsRankCollector collector =
                new MarketQuotationsRankCollector(type,
                        MarketQuotationsRankCollector.ORDER_BY_TURNOVER_RATE);
        List<Stock> stocks = collector.get();
        Assert.assertTrue(stocks.size() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongOrderBy() throws RemoteException {
        doTestOrderBy("wrong");
    }


    @Test
    public void testOrderBy() throws RemoteException {
        doTestOrderBy(MarketQuotationsRankCollector.ORDER_BY_AMOUNT);
        doTestOrderBy(MarketQuotationsRankCollector.ORDER_BY_PERCENT);
        doTestOrderBy(MarketQuotationsRankCollector.ORDER_BY_TURNOVER_RATE);
        doTestOrderBy(MarketQuotationsRankCollector.ORDER_BY_VOLUME);
    }


    private void doTestOrderBy(String orderBy) throws RemoteException {
        MarketQuotationsRankCollector collector =
                new MarketQuotationsRankCollector(StockType.HK, orderBy);
        List<Stock> stocks = collector.get();
        Assert.assertTrue(stocks.size() > 0);
    }


}
