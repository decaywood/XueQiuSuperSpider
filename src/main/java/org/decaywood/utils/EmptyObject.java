package org.decaywood.utils;

import org.decaywood.entity.*;
import org.decaywood.entity.selectorQuota.QuotaChainNode;
import org.decaywood.entity.trend.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * @author: decaywood
 * @date: 2015/11/24 20:47
 */

/**
 * 默认对象类，保存进行复用的默认对象
 * 有效防止空指针错误
 */
public abstract class EmptyObject {

    private static class EmptyCompanyInfo extends CompanyInfo {

        public EmptyCompanyInfo() {
            super(
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    new ArrayList<>());
        }
    }
    private static class EmptyCapitalFlow extends CapitalFlow {

        public EmptyCapitalFlow() {
            super(  emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    emptyString,
                    new ArrayList<>());
        }

        @Override
        public CapitalFlow copy() {
            return this;
        }
    }

    private static class EmptyQuotaChainNode implements QuotaChainNode {

        @Override
        public QuotaChainNode getNext() {
            return null;
        }

        @Override
        public void setNext(QuotaChainNode quotaChainNode) {}

        @Override
        public String generateQuotaRequest() {
            return "";
        }

        @Override
        public boolean end() {
            return true;
        }
    }

    private static class EmptyLongHuBangInfo extends LongHuBangInfo {

        public EmptyLongHuBangInfo() {
            super(emptyStock, new Date(), new HashSet<>(), new HashSet<>());
        }

        @Override
        public LongHuBangInfo copy() {
            return this;
        }
    }

    private static class EmptyRebalancing extends Rebalancing {

        public EmptyRebalancing() {
            super(new ArrayList<>());
        }

        @Override
        public EmptyRebalancing copy() {
            return this;
        }
    }

    private static class EmptyShareHoldersTrend extends ShareHoldersTrend {
        public EmptyShareHoldersTrend() {
            super(new ArrayList<>());
        }

        @Override
        public EmptyShareHoldersTrend copy() {
            return this;
        }
    }

    private static class EmptyMarketIndexTrend extends MarketIndexTrend {

        public EmptyMarketIndexTrend() {
            super(emptyString, emptyString, emptyString, emptyString, new ArrayList<>());
        }

        @Override
        public EmptyMarketIndexTrend copy() {
            return this;
        }
    }

    private static class EmptyStockTrend extends StockTrend {

        public EmptyStockTrend() {
            super(emptyString, Period.DAY, new ArrayList<>());
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
    public static EmptyQuotaChainNode emptyQuotaChainNode = new EmptyQuotaChainNode();
    public static EmptyShareHoldersTrend emptyShareHoldersTrend = new EmptyShareHoldersTrend();
    public static EmptyLongHuBangInfo emptyLongHuBangInfo = new EmptyLongHuBangInfo();
    public static EmptyCapitalFlow emptyCapitalFlow = new EmptyCapitalFlow();
    public static EmptyCompanyInfo emptyCompanyInfo = new EmptyCompanyInfo();
}
