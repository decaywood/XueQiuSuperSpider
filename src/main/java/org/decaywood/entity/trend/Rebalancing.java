package org.decaywood.entity.trend;

import org.decaywood.utils.StringUtils;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 17:15
 */

/**
 * 股票组合调仓记录
 */
public class Rebalancing extends Trend<Rebalancing.TrendBlock, Rebalancing> {


    /**
     * @param history 调仓历史
     */
    public Rebalancing(List<TrendBlock> history) {
        super(history);
    }


    /**
     * 历史节点
     */
    public static class TrendBlock implements ITrendBlock {
        private final String stock_name;
        private final String stock_symbol;
        private final String created_at;//time
        private final String prev_price;
        private final String price;
        private final String prev_weight;
        private final String target_weight;
        private final String weight;
        private final  String rebalancing_id;

        /**
         *
         * @param stock_name 股票名称
         * @param stock_symbol 股票代码
         * @param created_at 调仓时间
         * @param prev_price 上一次调仓价格
         * @param price 当前价格
         * @param prev_weight 上一次持仓比例
         * @param target_weight 期望持仓比例
         * @param weight 实际持仓比例
         * @param rebalancing_id 调仓节点ID
         */
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

        public String getStock_name() {
            return stock_name;
        }

        public String getStock_symbol() {
            return stock_symbol;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getPrev_price() {
            return prev_price;
        }

        public String getPrice() {
            return price;
        }

        public String getPrev_weight() {
            return prev_weight;
        }

        public String getTarget_weight() {
            return target_weight;
        }

        public String getWeight() {
            return weight;
        }

        public String getRebalancing_id() {
            return rebalancing_id;
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
