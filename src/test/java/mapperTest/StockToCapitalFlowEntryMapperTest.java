package mapperTest;

import org.decaywood.entity.Stock;
import org.decaywood.mapper.stockFirst.StockToCapitalFlowEntryMapper;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/2 12:34
 */
public class StockToCapitalFlowEntryMapperTest {

    @Test
    public void testFunc() throws RemoteException {
        StockToCapitalFlowEntryMapper mapper = new StockToCapitalFlowEntryMapper();
        List<Stock> stocks = TestCaseGenerator.generateStocks();
        boolean match = stocks
                .stream()
                .map(mapper)
                .allMatch(x -> x.getValue().getFiveDayInflows().size() == 5);
        Assert.assertTrue(match);
    }
}
