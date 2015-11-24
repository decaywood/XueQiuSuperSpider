package utils;

/**
 * @author: decaywood
 * @date: 2015/11/24 18:54
 */
public enum URLMapper {

    MAIN_PAGE("http://xueqiu.com"),
    COMPREHENSIVE_PAGE("http://xueqiu.com/hq"),

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
