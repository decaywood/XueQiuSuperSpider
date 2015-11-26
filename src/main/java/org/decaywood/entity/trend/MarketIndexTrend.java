package org.decaywood.entity.trend;

import org.decaywood.entity.DeepCopy;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 10:03
 */
public class MarketIndexTrend extends Trend<MarketIndexTrend.TrendBlock>
        implements DeepCopy<MarketIndexTrend> {


    private final String symbol;
    private final String name;

    public MarketIndexTrend(String symbol, String name, List<TrendBlock> history) {
        super(history);

        if(symbol == null || name == null)
            throw new IllegalArgumentException();

        this.name = name;
        this.symbol = symbol;
    }

    public static class TrendBlock {
        private final String time;
        private final String date;
        private final String value;
        private final String percent;

        public TrendBlock(String time, String date, String value, String percent) {
            if(time == null || date == null || value == null || percent == null)
                throw new IllegalArgumentException();
            this.time = time;
            this.date = date;
            this.value = value;
            this.percent = percent;
        }
    }

    @Override
    public MarketIndexTrend copy() {
        return new MarketIndexTrend(symbol, name, history);
    }

}
