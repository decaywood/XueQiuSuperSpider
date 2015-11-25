package org.decaywood.utils;

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
            super("", Period.ALL, new ArrayList<>());
        }

        @Override
        public StockTrend copy() {
            return this;
        }
    }

    public static StockTrend emptyStockTrend = new EmptyStockTrend();
    public static String emptyString = "";
    public static Date emptyDate = new Date(0);


}
