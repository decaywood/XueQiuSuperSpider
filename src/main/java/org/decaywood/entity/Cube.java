package org.decaywood.entity;

import org.decaywood.entity.trend.CubeTrend;
import org.decaywood.entity.trend.MarketIndexTrend;
import org.decaywood.utils.EmptyObject;

/**
 * @author: decaywood
 * @date: 2015/11/25 21:46.
 */
public class Cube implements DeepCopy<Cube> {

    private final String cubeID;
    private final String name;
    private final String symbol;

    private final String daily_gain;
    private final String monthly_gain;
    private final String annualized_gain_rate;
    private final String total_gain;




    private CubeTrend cubeTrend = EmptyObject.emptyCubeTrend;
    private MarketIndexTrend marketIndexTrend = EmptyObject.emptyMarketIndexTrend;

    public Cube(
            String cubeID,
            String name,
            String symbol,
            String daily_gain,
            String monthly_gain,
            String annualized_gain_rate,
            String total_gain
            ) {
        this.cubeID = cubeID;
        this.name = name;
        this.symbol = symbol;
        this.daily_gain = daily_gain;
        this.monthly_gain = monthly_gain;
        this.annualized_gain_rate = annualized_gain_rate;
        this.total_gain = total_gain;

    }


    public void setCubeTrend(CubeTrend cubeTrend) {
        this.cubeTrend = cubeTrend;
    }

    public void setMarketIndexTrend(MarketIndexTrend marketIndexTrend) {
        this.marketIndexTrend = marketIndexTrend;
    }

    @Override
    public Cube copy() {
        Cube cube = new Cube(
                cubeID,
                name,
                symbol,
                daily_gain,
                monthly_gain,
                annualized_gain_rate,
                total_gain);
        cube.setCubeTrend(cubeTrend);
        return cube;
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

    public CubeTrend getCubeTrend() {
        return cubeTrend;
    }

    public MarketIndexTrend getMarketIndexTrend() {
        return marketIndexTrend;
    }
}
