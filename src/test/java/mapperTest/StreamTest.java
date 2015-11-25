package mapperTest;

import org.junit.Test;

/**
 * @author: decaywood
 * @date: 2015/11/24 14:06
 */
public class StreamTest {

    @Test
    public void IntegrateTest() {
       /* CommissionIndustryCollector collector = new CommissionIndustryCollector();
        IndustryToIndustryWithStockEntryMapper mapper = new IndustryToIndustryWithStockEntryMapper();
        StockToStockWithAttributeMapper mapper1 = new StockToStockWithAttributeMapper();
        StockToStockWithTrendMapper mapper2 = new StockToStockWithTrendMapper();
        Map res = collector.get()
                .parallelStream()
                .peek(System.out::println)
                .map(mapper::apply)
                .flatMap(Collection::stream)
                .peek(x -> x.setValue(mapper1.apply(x.getValue())))
                .collect(Collectors.groupingBy(Entry::getKey));
        System.out.println(res);*/
    }

}
