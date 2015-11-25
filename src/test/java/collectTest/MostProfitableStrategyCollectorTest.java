package collectTest;

import org.decaywood.collector.MostProfitableStrategyCollector;
import org.decaywood.entity.Cube;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 22:46.
 */
public class MostProfitableStrategyCollectorTest {

    @Test
    public void testMarket() {
        doTest(MostProfitableStrategyCollector.Market.CN);
        doTest(MostProfitableStrategyCollector.Market.HK);
        doTest(MostProfitableStrategyCollector.Market.US);
    }

    @Test
    public void testORDER_BY() {
        doTest(MostProfitableStrategyCollector.ORDER_BY.DAILY);
        doTest(MostProfitableStrategyCollector.ORDER_BY.MONTHLY);
        doTest(MostProfitableStrategyCollector.ORDER_BY.YEARLY);
    }

    @Test
    public void testTopKMaxSize() throws Exception {
        MostProfitableStrategyCollector collector =
                new MostProfitableStrategyCollector(null, MostProfitableStrategyCollector.Market.CN
                        , MostProfitableStrategyCollector.ORDER_BY.DAILY, 1000);
        List<Cube> cubes = collector.get();
        Assert.assertTrue(cubes.size() <= MostProfitableStrategyCollector.CUBE_SIZE_SHRESHOLD);
    }

    @Test
    public void testTopKSize() throws Exception {
        int orderSize = 10;
        MostProfitableStrategyCollector collector =
                new MostProfitableStrategyCollector(null, MostProfitableStrategyCollector.Market.CN
                        , MostProfitableStrategyCollector.ORDER_BY.DAILY, orderSize);
        List<Cube> cubes = collector.get();
        Assert.assertTrue(cubes.size() == orderSize);
    }
    private void doTest(MostProfitableStrategyCollector.Market market) {
        MostProfitableStrategyCollector collector = new MostProfitableStrategyCollector(market);
        List<Cube> cubes = collector.get();
        Assert.assertFalse(cubes.isEmpty());
    }

    private void doTest(MostProfitableStrategyCollector.ORDER_BY order_by) {
        MostProfitableStrategyCollector collector = new MostProfitableStrategyCollector(
                MostProfitableStrategyCollector.Market.CN,
                order_by);
        List<Cube> cubes = collector.get();
        Assert.assertFalse(cubes.isEmpty());
    }
}
