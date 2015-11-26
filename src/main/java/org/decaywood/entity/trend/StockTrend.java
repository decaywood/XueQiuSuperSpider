package org.decaywood.entity.trend;

import org.decaywood.entity.DeepCopy;
import org.decaywood.utils.DateParser;
import org.decaywood.utils.StringChecker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:00
 */
public class StockTrend extends Trend<StockTrend.TrendBlock> implements DeepCopy<StockTrend> {

    public enum Period

    {
        ONE_DAY("1d"),
        FIVE_DAY("5d"),
        ONE_MONTH("1m"),
        SIX_MONTH("6m"),
        ONE_YEAR("1y"),
        THREE_YEAR("3y"),
        ALL("all");

        private String val;

        Period(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }

    public static class TrendBlock {

        private final String volume;
        private final String avg_price;
        private final String current;
        private final Date time;

        public TrendBlock(String volume, String avg_price, String current, String time) {
            if(StringChecker.nullOrEmpty(volume, avg_price, current, time))
                throw new IllegalArgumentException();
            this.volume = volume;
            this.avg_price = avg_price;
            this.current = current;
            this.time = DateParser.parseToDate(time);
        }
    }


    private final Period period;
    private final String stockNo;

    public StockTrend(String stockNo, Period period, List<TrendBlock> history) {
        super(history);
        this.period = period;
        this.stockNo = stockNo;
    }


    public Period getPeriod() {
        return period;
    }

    public String getStockNo() {
        return stockNo;
    }

    @Override
    public StockTrend copy() {
        return new StockTrend(stockNo, period, new ArrayList<>(history));
    }

}
