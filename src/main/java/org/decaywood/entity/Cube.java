package org.decaywood.entity;

import org.decaywood.entity.trend.CubeTrend;
import org.decaywood.entity.trend.MarketIndexTrend;
import org.decaywood.entity.trend.Rebalancing;
import org.decaywood.utils.EmptyObject;

/**
 * @author: decaywood
 * @date: 2015/11/25 21:46.
 */
public class Cube implements DeepCopy<Cube> {

    private final String cubeID;
    private final String name;
    private final String symbol;

    private String daily_gain = EmptyObject.emptyString;
    private String monthly_gain = EmptyObject.emptyString;
    private String annualized_gain_rate = EmptyObject.emptyString;
    private String total_gain = EmptyObject.emptyString;


    private CubeTrend cubeTrend = EmptyObject.emptyCubeTrend;
    private MarketIndexTrend marketIndexTrend = EmptyObject.emptyMarketIndexTrend;
    private Rebalancing rebalancing = EmptyObject.emptyRebalancing;

    public Cube(String cubeID, String name, String symbol) {
        this.cubeID = cubeID;
        this.name = name;
        this.symbol = symbol;
    }


    @Override
    public Cube copy() {
        Cube cube = new Cube(cubeID, name, symbol);

        cube.setDaily_gain(daily_gain);
        cube.setMonthly_gain(monthly_gain);
        cube.setAnnualized_gain_rate(annualized_gain_rate);
        cube.setTotal_gain(total_gain);

        cube.setCubeTrend(cubeTrend.copy());
        cube.setMarketIndexTrend(marketIndexTrend.copy());
        cube.setRebalancing(rebalancing);

        return cube;
    }


    public void setCubeTrend(CubeTrend cubeTrend) {
        this.cubeTrend = cubeTrend;
    }

    public void setMarketIndexTrend(MarketIndexTrend marketIndexTrend) {
        this.marketIndexTrend = marketIndexTrend;
    }

    public void setRebalancing(Rebalancing rebalancing) {
        this.rebalancing = rebalancing;
    }

    public void setDaily_gain(String daily_gain) {
        this.daily_gain = daily_gain;
    }

    public void setMonthly_gain(String monthly_gain) {
        this.monthly_gain = monthly_gain;
    }

    public void setTotal_gain(String total_gain) {
        this.total_gain = total_gain;
    }


    public void setAnnualized_gain_rate(String annualized_gain_rate) {
        this.annualized_gain_rate = annualized_gain_rate;
    }



    public String getDaily_gain() {
        return daily_gain;
    }

    public String getMonthly_gain() {
        return monthly_gain;
    }

    public String getAnnualized_gain_rate() {
        return annualized_gain_rate;
    }

    public String getTotal_gain() {
        return total_gain;
    }


    public String getCubeID() {
        return cubeID;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Rebalancing getRebalancing() {
        return rebalancing;
    }

    public CubeTrend getCubeTrend() {
        return cubeTrend;
    }

    public MarketIndexTrend getMarketIndexTrend() {
        return marketIndexTrend;
    }
}
