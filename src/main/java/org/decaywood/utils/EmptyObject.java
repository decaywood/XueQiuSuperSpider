package org.decaywood.utils;

import org.decaywood.entity.Cube;
import org.decaywood.entity.Industry;
import org.decaywood.entity.Stock;
import org.decaywood.entity.StockTrend;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author: decaywood
 * @date: 2015/11/24 20:47
 */
public abstract class EmptyObject {

    private static class EmptyStockTrend extends StockTrend {

        public EmptyStockTrend() {
            super(emptyString, Period.ALL, new ArrayList<>());
        }

        @Override
        public StockTrend copy() {
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

}
