package org.decaywood.entity.trend;

import org.decaywood.utils.StringUtils;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 17:15
 */
public class Rebalancing extends Trend<Rebalancing.TrendBlock, Rebalancing> {



    public Rebalancing(List<TrendBlock> history) {
        super(history);
    }


    public static class TrendBlock {
        private final String stock_name;
        private final String stock_symbol;
        private final String created_at;//time
        private final String prev_price;
        private final String price;
        private final String prev_weight;
        private final String target_weight;
        private final String weight;
        private final  String rebalancing_id;

        public TrendBlock(String stock_name,
                          String stock_symbol,
                          String created_at,
                          String prev_price,
                          String price,
                          String prev_weight,
                          String target_weight,
                          String weight,
                          String rebalancing_id
        ) {

            if(StringUtils.nullOrEmpty(stock_name, stock_symbol, created_at
                    , prev_price, price, prev_weight, target_weight, weight, rebalancing_id))
                throw new IllegalArgumentException();

            this.stock_name = stock_name;
            this.stock_symbol = stock_symbol;
            this.created_at = created_at;
            this.prev_price = prev_price;
            this.price = price;
            this.prev_weight = prev_weight;
            this.target_weight = target_weight;
            this.weight = weight;
            this.rebalancing_id = rebalancing_id;
        }

        @Override
        public String toString() {
            return "TrendBlock{" +
                    "stock_name='" + stock_name + '\'' +
                    ", stock_symbol='" + stock_symbol + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", prev_price='" + prev_price + '\'' +
                    ", price='" + price + '\'' +
                    ", prev_weight='" + prev_weight + '\'' +
                    ", target_weight='" + target_weight + '\'' +
                    ", weight='" + weight + '\'' +
                    ", rebalancing_id='" + rebalancing_id + '\'' +
                    '}';
        }
    }


    @Override
    public Rebalancing copy() {
        return new Rebalancing(history);
    }

}
