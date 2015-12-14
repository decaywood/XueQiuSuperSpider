package mapperTest;

import org.decaywood.entity.LongHuBangInfo;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.stockFirst.StockToLongHuBangMapper;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: decaywood
 * @date: 2015/11/27 15:46
 */
public class StockToLongHuBangMapperTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test()
    public void testWrongArgument() throws Exception{

        Stock stock = new Stock("中飞股份", "SZ300489");
        StockToLongHuBangMapper mapper = new StockToLongHuBangMapper();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("lost parameter: stockQueryDate");
        mapper.mapLogic(stock);
    }

    @Test
    public void testFunction() throws RemoteException {
        Stock stock = new Stock("中飞股份", "SZ300489");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.NOVEMBER, 25);
        Date since = calendar.getTime();
        stock.setStockQueryDate(since);

        LongHuBangInfo info = new StockToLongHuBangMapper().apply(stock);

        Assert.assertFalse(info.getTopBuyList().isEmpty());
        Assert.assertFalse(info.getTopSaleList().isEmpty());
    }
}
