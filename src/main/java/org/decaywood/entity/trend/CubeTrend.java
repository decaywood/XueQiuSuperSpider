package org.decaywood.entity.trend;

import org.decaywood.entity.DeepCopy;
import org.decaywood.utils.StringChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 23:16.
 */
public class CubeTrend extends Trend<CubeTrend.TrendBlock> implements DeepCopy<CubeTrend> {

    private final String symbol;
    private final String name;
    private final String from;
    private final String to;



    public CubeTrend(String symbol, String name, String from, String to, List<TrendBlock> history) {
        super(history);
        if(symbol == null || name == null || from == null || to == null)
            throw new IllegalArgumentException();
        this.symbol = symbol;
        this.name = name;
        this.from = from;
        this.to = to;
    }



    public static class TrendBlock {

        private final String time;
        private final String date;
        private final String value;
        private final String percent;

        public TrendBlock(String time, String date, String value, String percent) {
            if(StringChecker.nullOrEmpty(time, date, value, percent))
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

    @Override
    public CubeTrend copy() {
        return new CubeTrend(symbol, name, from, to, new ArrayList<>(history));
    }

}
