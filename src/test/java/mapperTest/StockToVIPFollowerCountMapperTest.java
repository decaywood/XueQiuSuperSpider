package mapperTest;

import org.decaywood.entity.Stock;
import org.decaywood.mapper.stockFirst.StockToVIPFollowerCountMapper;
import org.junit.Test;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/30 17:06
 */
public class StockToVIPFollowerCountMapperTest {


    @Test
    public void testFunc() {

        List<Stock> stocks = TestCaseGenerator.generateStocks();
        StockToVIPFollowerCountMapper mapper = new StockToVIPFollowerCountMapper();
        stocks.parallelStream().forEach(x -> {
            int count = mapper.apply(x);
            System.out.println(x.getStockName() + "粉丝过万的关注者人数为： " + count + " 人");
        });
    }
}
