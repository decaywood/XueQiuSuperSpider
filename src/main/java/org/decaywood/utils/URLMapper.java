package org.decaywood.utils;

/**
 * @author: decaywood
 * @date: 2015/11/24 18:54
 */
public enum URLMapper {

    /*--------------------------------  Xue Qiu     --------------------------------------*/

    MAIN_PAGE("http://xueqiu.com"),
    COMPREHENSIVE_PAGE("http://xueqiu.com/hq"),

    HU_SHEN_NEWS_REF_JSON("http://xueqiu.com/statuses/topic.json"),
    STOCK_SHAREHOLDERS_JSON("http://xueqiu.com/stock/f10/shareholdernum.json"),
    STOCK_SELECTOR_JSON("http://xueqiu.com/stock/screener/screen.json"),
    LONGHUBANG_JSON("http://xueqiu.com/stock/f10/bizunittrdinfo.json"),
    STOCK_INDUSTRY_JSON("http://xueqiu.com/stock/f10/compinfo.json"),
    CUBE_REBALANCING_JSON("http://xueqiu.com/cubes/rebalancing/history.json"),
    CUBE_TREND_JSON("http://xueqiu.com/cubes/nav_daily/all.json"),
    CUBES_RANK_JSON("http://xueqiu.com/cubes/discover/rank/cube/list.json"),
    MARKET_QUOTATIONS_RANK_JSON("http://xueqiu.com/stock/quote_order.json"),
    SCOPE_STOCK_RANK_JSON("http://xueqiu.com/stock/rank.json"),
    STOCK_TREND_JSON("http://xueqiu.com/stock/forchartk/stocklist.json"),
    STOCK_JSON("http://xueqiu.com/v4/stock/quote.json"),
    INDUSTRY_JSON("http://xueqiu.com/industry/quote_order.json"),

    /*--------------------------------  NetEase     --------------------------------------*/

    NETEASE_MAIN_PAGE("http://quotes.money.163.com/stock"),
    STOCK_CAPITAL_FLOW("http://quotes.money.163.com/service/zjlx_chart.html");


    URLMapper(String URL) {
        this.URL = URL;
    }

    private String URL;

    @Override
    public String toString() {
        return URL;
    }
}
