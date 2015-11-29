package org.decaywood.mapper.stockFirst;

import org.decaywood.entity.Stock;
import org.decaywood.mapper.AbstractMapper;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;

import java.util.Date;

/**
 * @author: decaywood
 * @date: 2015/11/29 22:01.
 */
public class StockToStockWithShareHolderTrendMapper extends AbstractMapper<Stock, Stock> {

    public StockToStockWithShareHolderTrendMapper(Date from, Date to) {
        this(null, from, to);
    }

    public StockToStockWithShareHolderTrendMapper(TimeWaitingStrategy strategy, Date from, Date to) {
        super(strategy);
    }


    @Override
    public Stock mapLogic(Stock stock) throws Exception {
        return null;
    }


}
