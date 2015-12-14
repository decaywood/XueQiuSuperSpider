package org.decaywood.entity.trend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/24 16:00
 */
public class StockTrend extends Trend<StockTrend.TrendBlock, StockTrend> {

    public enum Period

    {
        DAY("1day"),
        WEEK("1week"),
        MONTH("1month");

        private String val;

        Period(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return val;
        }
    }

    public static class TrendBlock implements ITrendBlock {
        private final String volume;//成交量
        private final String open;//开盘价
        private final String high;//最高
        private final String close;//收盘价
        private final String low;//最低
        private final String chg;//涨跌额
        private final String percent;//涨跌幅
        private final String turnrate;//换手率
        private final String ma5;//5日线
        private final String ma10;//10日线
        private final String ma20;//20日线
        private final String ma30;//30日线
        private final String dif;//DIF
        private final String dea;//DEA
        private final String macd;//MACD
        private final String time;//时间节点


        /**
         *
         * @param volume 成交量
         * @param open 开盘价
         * @param high 最高
         * @param close 收盘价
         * @param low 最低
         * @param chg 涨跌额
         * @param percent 涨跌幅
         * @param turnrate 换手率
         * @param ma5 5日线
         * @param ma10 10日线
         * @param ma20 20日线
         * @param ma30 30日线
         * @param dif DIF
         * @param dea DEA
         * @param macd MACD
         * @param time 时间节点
         */
        public TrendBlock(String volume,
                          String open,
                          String high,
                          String close,
                          String low,
                          String chg,
                          String percent,
                          String turnrate,
                          String ma5,
                          String ma10,
                          String ma20,
                          String ma30,
                          String dif,
                          String dea,
                          String macd,
                          String time) {
            this.volume = volume;
            this.open = open;
            this.high = high;
            this.close = close;
            this.low = low;
            this.chg = chg;
            this.percent = percent;
            this.turnrate = turnrate;
            this.ma5 = ma5;
            this.ma10 = ma10;
            this.ma20 = ma20;
            this.ma30 = ma30;
            this.dif = dif;
            this.dea = dea;
            this.macd = macd;
            this.time = time;
        }


        public String getVolume() {
            return volume;
        }

        public String getOpen() {
            return open;
        }

        public String getHigh() {
            return high;
        }

        public String getClose() {
            return close;
        }

        public String getLow() {
            return low;
        }

        public String getChg() {
            return chg;
        }

        public String getPercent() {
            return percent;
        }

        public String getTurnrate() {
            return turnrate;
        }

        public String getMa5() {
            return ma5;
        }

        public String getMa10() {
            return ma10;
        }

        public String getMa20() {
            return ma20;
        }

        public String getMa30() {
            return ma30;
        }

        public String getDif() {
            return dif;
        }

        public String getDea() {
            return dea;
        }

        public String getMacd() {
            return macd;
        }

        public String getTime() {
            return time;
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
