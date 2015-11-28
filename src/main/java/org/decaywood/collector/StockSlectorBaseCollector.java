package org.decaywood.collector;

import org.decaywood.entity.Stock;
import org.decaywood.timeWaitingStrategy.TimeWaitingStrategy;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/28 12:40
 */
public class StockSlectorBaseCollector extends AbstractCollector<List<Stock>> {



    public StockSlectorBaseCollector(TimeWaitingStrategy strategy) {
        super(strategy);
    }

    @Override
    public List<Stock> collectLogic() throws Exception {
        return null;
    }
}
