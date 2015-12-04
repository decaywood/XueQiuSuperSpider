package org.decaywood.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/12/2 12:44
 */
public class CapitalFlow implements DeepCopy<CapitalFlow> {

    private final String capitalInflow;//资金流向（万）
    private final String largeQuantity;//大单（万）
    private final String midQuantity;//中单（万）
    private final String smallQuantity;//小单（万）

    private final String largeQuantBuy;//大单主动买入（手）
    private final String largeQuantSell;//大单主动卖出（手）

    private final String largeQuantDealProp;//大单成交占比（%）

    private final String fiveDayInflow;
    private final List<Double> fiveDayInflows;

    public CapitalFlow(String capitalInflow,
                       String largeQuantity,
                       String midQuantity,
                       String smallQuantity,
                       String largeQuantBuy,
                       String largeQuantSell,
                       String largeQuantDealProp,
                       String fiveDayInflow,
                       List<Double> fiveDayInflows) {

        this.capitalInflow = capitalInflow;
        this.largeQuantity = largeQuantity;
        this.midQuantity = midQuantity;
        this.smallQuantity = smallQuantity;
        this.largeQuantBuy = largeQuantBuy;
        this.largeQuantSell = largeQuantSell;
        this.largeQuantDealProp = largeQuantDealProp;
        this.fiveDayInflow = fiveDayInflow;
        this.fiveDayInflows = fiveDayInflows;
    }



    public String getCapitalInflow() {
        return capitalInflow;
    }

    public String getLargeQuantity() {
        return largeQuantity;
    }

    public String getMidQuantity() {
        return midQuantity;
    }

    public String getSmallQuantity() {
        return smallQuantity;
    }

    public String getLargeQuantBuy() {
        return largeQuantBuy;
    }

    public String getLargeQuantSell() {
        return largeQuantSell;
    }

    public String getLargeQuantDealProp() {
        return largeQuantDealProp;
    }

    public String getFiveDayInflow() {
        return fiveDayInflow;
    }

    public List<Double> getFiveDayInflows() {
        return fiveDayInflows;
    }

    @Override
    public CapitalFlow copy() {
        return new CapitalFlow(
                capitalInflow,
                largeQuantity,
                midQuantity,
                smallQuantity,
                largeQuantBuy,
                largeQuantSell,
                largeQuantDealProp,
                fiveDayInflow,
                new ArrayList<>(fiveDayInflows));
    }
}
