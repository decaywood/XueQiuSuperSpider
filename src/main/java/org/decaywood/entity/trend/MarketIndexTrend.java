package org.decaywood.entity.trend;

import org.decaywood.utils.StringUtils;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 10:03
 */
public class MarketIndexTrend extends Trend<MarketIndexTrend.TrendBlock, MarketIndexTrend> {


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
            if(StringUtils.nullOrEmpty(time, date, value, percent))
                throw new IllegalArgumentException();
            this.time = time;
            this.date = date;
            this.value = value;
            this.percent = percent;
        }

        public String getTime() {
            return time;
        }

        public String getDate() {
            return date;
        }

        public String getValue() {
            return value;
        }

        public String getPercent() {
            return percent;
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public MarketIndexTrend copy() {
        return new MarketIndexTrend(symbol, name, history);
    }

}
