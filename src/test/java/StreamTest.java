import org.decaywood.collector.DateRangeCollector;
import org.decaywood.entity.LongHuBangInfo;
import org.decaywood.mapper.dateFirst.DateToLongHuBangStockMapper;
import org.decaywood.mapper.stockFirst.StockToLongHuBangMapper;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: decaywood
 * @date: 2015/11/24 14:06
 */
public class StreamTest {

    @Test
    public void IntegrateTest() {
       /* CommissionIndustryCollector collector = new CommissionIndustryCollector();
        IndustryToStocksMapper mapper = new IndustryToStocksMapper();
        StockToStockWithAttributeMapper mapper1 = new StockToStockWithAttributeMapper();
        StockToStockWithTrendMapper mapper2 = new StockToStockWithTrendMapper();
        Map res = collector.get()
                .parallelStream()
                .map(mapper)
        System.out.println(res);*/
    }



    //游资追踪
    @Test
    public void LongHuBangTracking() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.OCTOBER, 20);
        Date from = calendar.getTime();
        calendar.set(2015, Calendar.NOVEMBER, 25);
        Date to = calendar.getTime();
        DateRangeCollector collector = new DateRangeCollector(from, to);
        DateToLongHuBangStockMapper mapper = new DateToLongHuBangStockMapper();
        StockToLongHuBangMapper mapper1 = new StockToLongHuBangMapper();
        List<LongHuBangInfo> s = collector.get()
                .parallelStream()
                .map(mapper)
                .flatMap(Collection::stream).map(mapper1)
                .filter(x -> x.bizsunitInBuyList("中信证券股份有限公司上海溧阳路证券营业部")
                || x.bizsunitInSaleList("中信证券股份有限公司上海溧阳路证券营业部"))
                .sorted(Comparator.comparing(LongHuBangInfo::getDate))
                .collect(Collectors.toList());
        for (LongHuBangInfo info : s) {
            System.out.println(info.getDate() + " -> " + info.getStock().getStockName());
        }

    }

}
