package org.decaywood.utils;

/**
 * @author: decaywood
 * @date: 2015/11/24 18:54
 */
public enum URLMapper {

    MAIN_PAGE("http://xueqiu.com"),
    COMPREHENSIVE_PAGE("http://xueqiu.com/hq"),


    CUBE_REBALANCING_JSON("http://xueqiu.com/cubes/rebalancing/history.json"),
    CUBE_TREND_JSON("http://xueqiu.com/cubes/nav_daily/all.json"),
    CUBES_RANK_JSON("http://xueqiu.com/cubes/discover/rank/cube/list.json"),
    MARKET_QUOTATIONS_RANK_JSON("http://xueqiu.com/stock/quote_order.json"),
    SCOPE_STOCK_RANK_JSON("http://xueqiu.com/stock/rank.json"),
    STOCK_TREND_JSON("http://xueqiu.com/stock/forchart/stocklist.json"),
    STOCK_JSON("http://xueqiu.com/v4/stock/quote.json"),
    INDUSTRY_JSON("http://xueqiu.com/industry/quote_order.json");



    URLMapper(String URL) {
        this.URL = URL;
    }

    private String URL;

    @Override
    public String toString() {
        return URL;
    }
}
