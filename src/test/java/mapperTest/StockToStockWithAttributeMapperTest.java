package mapperTest;

import org.decaywood.entity.Stock;
import org.decaywood.mapper.stockFirst.StockToStockWithAttributeMapper;
import org.decaywood.utils.EmptyObject;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 19:25
 */
public class StockToStockWithAttributeMapperTest {

    @Test
    public void testFunction() throws RemoteException {
        List<Stock> industries = TestCaseGenerator.generateStocks();
        StockToStockWithAttributeMapper mapper = new StockToStockWithAttributeMapper();
        industries.stream().map(mapper)
        .forEach(StockToStockWithAttributeMapperTest::checkNotNull);
    }

    @Test
    public void testNull() throws RemoteException {
        StockToStockWithAttributeMapper mapper = new StockToStockWithAttributeMapper();
        Assert.assertEquals(EmptyObject.emptyStock, mapper.apply(null));
        Assert.assertEquals(EmptyObject.emptyStock, mapper.apply(EmptyObject.emptyStock));
    }

    private static void checkNotNull(Stock stock) {

        Assert.assertNotEquals(stock.getTime(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getPsr(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getAmount(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getDividend(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getStockPageSite(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getPe_lyr(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getStockTrend(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getFloat_market_capital(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getHigh52Week(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getTotalShares(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getNet_assets(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getRise_stop(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getPe_ttm(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getHigh(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getStockNo(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getVolume(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getLow52week(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getStockName(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getCurrency_unit(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getChange(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getClose(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getCurrent(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getOpen(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getPercentage(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getEps(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getLow(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getTurnover_rate(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getFall_stop(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getMarketCapital(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getFloat_shares(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getAmplitude(), EmptyObject.emptyString);
        Assert.assertNotEquals(stock.getLast_close(), EmptyObject.emptyString);

    }



}
