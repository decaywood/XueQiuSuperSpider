package org.decaywood.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/25 23:16.
 */
public class CubeTrend implements DeepCopy<CubeTrend> {

    private final String symbol;
    private final String name;
    private final String from;
    private final String to;
    private final List<TrendBlock> history;

    public CubeTrend(String symbol, String name, String from, String to, List<TrendBlock> history) {
        this.symbol = symbol;
        this.name = name;
        this.from = from;
        this.to = to;
        this.history = history;
    }



    public static class TrendBlock {
        private final String time;
        private final String date;
        private final String value;
        private final String percent;

        public TrendBlock(String time, String date, String value, String percent) {
            this.time = time;
            this.date = date;
            this.value = value;
            this.percent = percent;
        }
    }

    @Override
    public CubeTrend copy() {
        return new CubeTrend(symbol, name, from, to, new ArrayList<>(history));
    }

}
