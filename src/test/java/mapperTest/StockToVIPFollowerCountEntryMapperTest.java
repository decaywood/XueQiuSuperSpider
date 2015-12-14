package mapperTest;

import org.decaywood.entity.Entry;
import org.decaywood.entity.Stock;
import org.decaywood.mapper.stockFirst.StockToVIPFollowerCountEntryMapper;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/30 17:06
 */
public class StockToVIPFollowerCountEntryMapperTest {


    @Test
    public void testFunc() throws RemoteException {

        List<Stock> stocks = TestCaseGenerator.generateStocks();
        StockToVIPFollowerCountEntryMapper mapper = new StockToVIPFollowerCountEntryMapper();
        stocks.parallelStream().forEach(x -> {
            Entry<Stock, Integer> count = mapper.apply(x);
            System.out.println(x.getStockName() + "粉丝过万的关注者人数为： " + count.getValue() + " 人");
        });
    }
}
