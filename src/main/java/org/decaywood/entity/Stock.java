package org.decaywood.entity;

import org.decaywood.utils.DateParser;
import org.decaywood.utils.EmptyObject;

import java.util.Date;

/**
 * @author: decaywood
 * @date: 2015/11/23 19:14
 */
public class Stock implements DeepCopy<Stock> {

    private static final String Stock_Page_PreFix = "http://xueqiu.com/S/";

    private final String stockName; //股票名称
    private final String stockNo; //股票代码

    private String currency_unit = EmptyObject.emptyString;//币种
    private String current = EmptyObject.emptyString; //当前价
    private String volume = EmptyObject.emptyString;//成交量
    private String percentage = EmptyObject.emptyString;//百分比
    private String change = EmptyObject.emptyString;//涨跌幅
    private String open = EmptyObject.emptyString;//开盘价
    private String high = EmptyObject.emptyString;//最高
    private String low = EmptyObject.emptyString;//最低
    private String amplitude = EmptyObject.emptyString;//振幅
    private String fall_stop = EmptyObject.emptyString;//跌停价
    private String rise_stop = EmptyObject.emptyString;//涨停价
    private String close = EmptyObject.emptyString;//收盘价
    private String last_close = EmptyObject.emptyString;//昨收
    private String high52Week = EmptyObject.emptyString;//52周最高
    private String low52week = EmptyObject.emptyString;//52周最低
    private String marketCapital = EmptyObject.emptyString;//总市值
    private String float_market_capital = EmptyObject.emptyString;//流通值
    private String float_shares = EmptyObject.emptyString;//流通股本
    private String totalShares = EmptyObject.emptyString;//总股本
    private String eps = EmptyObject.emptyString;//每股收益
    private String net_assets = EmptyObject.emptyString;//每股净资产
    private String pe_ttm = EmptyObject.emptyString;//ttm = EmptyEntity.emptyString;
    private String pe_lyr = EmptyObject.emptyString;//lyr = EmptyEntity.emptyString;
    private String dividend = EmptyObject.emptyString;//每股股息
    private String psr = EmptyObject.emptyString;//市销率
    private String turnover_rate = EmptyObject.emptyString;//换手
    private String amount = EmptyObject.emptyString;//成交额
    private Date time = EmptyObject.emptyDate;//雪球系统时间

    private StockTrend stockTrend = EmptyObject.emptyStockTrend;//股票走势




    public Stock(final String stockName, final String stockNo) {
        this.stockName = stockName;
        this.stockNo = stockNo;
    }

    public String getStockName() {
        return stockName;
    }

    public String getStockNo() {
        return stockNo;
    }


    public String getCurrency_unit() {
        return currency_unit;
    }

    public void setCurrency_unit(String currency_unit) {
        this.currency_unit = currency_unit;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getAmplitude() {
        return amplitude;
    }


    public void setTurnover_rate(String turnover_rate) {
        this.turnover_rate = turnover_rate;
    }

    public String getTurnover_rate() {
        return turnover_rate;
    }

    public void setAmplitude(String amplitude) {
        this.amplitude = amplitude;
    }

    public String getFall_stop() {
        return fall_stop;
    }

    public void setFall_stop(String fall_stop) {
        this.fall_stop = fall_stop;
    }

    public String getRise_stop() {
        return rise_stop;
    }

    public void setRise_stop(String rise_stop) {
        this.rise_stop = rise_stop;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getLast_close() {
        return last_close;
    }

    public void setLast_close(String last_close) {
        this.last_close = last_close;
    }

    public String getHigh52Week() {
        return high52Week;
    }

    public void setHigh52Week(String high52Week) {
        this.high52Week = high52Week;
    }

    public String getLow52week() {
        return low52week;
    }

    public void setLow52week(String low52week) {
        this.low52week = low52week;
    }

    public String getMarketCapital() {
        return marketCapital;
    }

    public void setMarketCapital(String marketCapital) {
        this.marketCapital = marketCapital;
    }

    public String getFloat_market_capital() {
        return float_market_capital;
    }

    public void setFloat_market_capital(String float_market_capital) {
        this.float_market_capital = float_market_capital;
    }

    public String getFloat_shares() {
        return float_shares;
    }

    public void setFloat_shares(String float_shares) {
        this.float_shares = float_shares;
    }

    public String getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(String totalShares) {
        this.totalShares = totalShares;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getNet_assets() {
        return net_assets;
    }

    public void setNet_assets(String net_assets) {
        this.net_assets = net_assets;
    }

    public String getPe_ttm() {
        return pe_ttm;
    }

    public void setPe_ttm(String pe_ttm) {
        this.pe_ttm = pe_ttm;
    }

    public String getPe_lyr() {
        return pe_lyr;
    }

    public void setPe_lyr(String pe_lyr) {
        this.pe_lyr = pe_lyr;
    }

    public String getDividend() {
        return dividend;
    }

    public void setDividend(String dividend) {
        this.dividend = dividend;
    }

    public String getPsr() {
        return psr;
    }

    public void setPsr(String psr) {
        this.psr = psr;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = DateParser.parseToDate(time);
    }

    public StockTrend getStockTrend() {
        return stockTrend;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setStockTrend(StockTrend stockTrend) {
        this.stockTrend = stockTrend;
    }

    public String getStockPageSite() {
        return Stock_Page_PreFix + stockNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public Stock copy() {
        Stock stock = new Stock(this.stockName, this.stockNo);
        stock.currency_unit = currency_unit;
        stock.current = current;
        stock.volume = volume;
        stock.percentage = percentage;
        stock.change = change;
        stock.open = open;
        stock.high = high;
        stock.low = low;
        stock.amplitude = amplitude;
        stock.rise_stop = rise_stop;
        stock.fall_stop = fall_stop;
        stock.close = close;
        stock.last_close = last_close;
        stock.high52Week = high52Week;
        stock.low52week = low52week;
        stock.marketCapital = marketCapital;
        stock.float_market_capital = float_market_capital;
        stock.float_shares = float_shares;
        stock.totalShares = totalShares;
        stock.eps = eps;
        stock.net_assets = net_assets;
        stock.pe_ttm = pe_ttm;
        stock.pe_lyr = pe_lyr;
        stock.dividend = dividend;
        stock.psr = psr;
        stock.time = time;
        stock.amount = amount;
        stock.turnover_rate = turnover_rate;
        stock.stockTrend = stockTrend.copy();
        return stock;
    }


}
