package org.decaywood.mapper.stockFirst;

import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;

/**
 * @author: decaywood
 * @date: 2015/11/30 16:13
 */
public class StockToStockWithIndustryMapper extends AbstractMapper <Stock, Stock> {


    public StockToStockWithIndustryMapper(TimeWaitingStrategy strategy) {
        super(strategy);
    }

    @Override
    public Stock mapLogic(Stock stock) throws Exception {
        return null;
    }


}
