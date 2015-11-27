package org.decaywood.utils;

import org.decaywood.entity.*;
import org.decaywood.entity.trend.CubeTrend;
import org.decaywood.entity.trend.MarketIndexTrend;
import org.decaywood.entity.trend.Rebalancing;
import org.decaywood.entity.trend.StockTrend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/24 20:47
 */
public abstract class EmptyObject {

    private static class EmptyRebalancing extends Rebalancing {

        public EmptyRebalancing() {
            super(new ArrayList<>());
        }

        @Override
        public EmptyRebalancing copy() {
            return this;
        }
    }

    private static class EmptyMarketIndexTrend extends MarketIndexTrend {

        public EmptyMarketIndexTrend() {
            super(emptyString, emptyString, new ArrayList<>());
        }

        @Override
        public EmptyMarketIndexTrend copy() {
            return this;
        }
    }

    private static class EmptyStockTrend extends StockTrend {

        public EmptyStockTrend() {
            super(emptyString, Period.ALL, new ArrayList<>());
        }

        @Override
        public StockTrend copy() {
            return this;
        }
    }

    private static class EmptyCubeTrend extends CubeTrend {

        public EmptyCubeTrend() {
            super(
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    new ArrayList<>());
        }

        @Override
        public CubeTrend copy() {
            return this;
        }
    }

    private static class EmptyIndustry extends Industry {

        public EmptyIndustry() {
            super(emptyString, emptyString);
        }

        @Override
        public Industry copy() {
            return this;
        }
    }

    private static class EmptyStock extends Stock {


        public EmptyStock() {
            super(emptyString, emptyString);
        }

        @Override
        public Stock copy() {
            return this;
        }
    }

    private static class EmptyCube extends Cube {


        public EmptyCube() {
            super(emptyString, emptyString, emptyString);
        }

        @Override
        public Cube copy() {
            return this;
        }
    }

    public static StockTrend emptyStockTrend = new EmptyStockTrend();
    public static String emptyString = "*";
    public static Date emptyDate = new Date(0);
    public static Industry emptyIndustry = new EmptyIndustry();
    public static Stock emptyStock = new EmptyStock();
    public static Cube emptyCube = new EmptyCube();
    public static CubeTrend emptyCubeTrend = new EmptyCubeTrend();
    public static MarketIndexTrend emptyMarketIndexTrend = new EmptyMarketIndexTrend();
    public static Rebalancing emptyRebalancing = new EmptyRebalancing();
}
