package entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author: decaywood
 * @date: 2015/11/23 19:14
 */
public class Stock {

    private static final String Stock_Page_PreFix = "http://xueqiu.com/S/";

    private final String stockName; //股票名称
    private final String stockNo; //股票代码

    private String currency_unit;//币种
    private String current; //当前价
    private String volume;//成交量
    private String percentage;//百分比
    private String change;//涨跌幅
    private String open;//开盘价
    private String high;//最高
    private String low;//最低
    private String amplitude;//振幅
    private String fall_stop;//跌停价
    private String rise_stop;//涨停价
    private String close;//收盘价
    private String last_close;//昨收
    private String high52Week;//52周最高
    private String low52week;//52周最低
    private String marketCapital;//总市值
    private String float_market_capital;//流通值
    private String float_shares;//流通股本
    private String totalShares;//总股本
    private String eps;//每股收益
    private String net_assets;//每股净资产
    private String pe_ttm;//ttm;
    private String pe_lyr;//lyr;
    private String dividend;//每股股息
    private String psr;//市销率

    private Date time;//雪球系统时间

    DateFormat dateFormat;

    public Stock(final String stockName, final String stockNo) {
        this.stockName = stockName;
        this.stockNo = stockNo;
        this.dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy", Locale.ENGLISH);
    }

    public String getKeyName() {
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
        try {
            this.time = dateFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String getStockPageSite() {
        return Stock_Page_PreFix + stockNo;
    }

    public Stock deepCopy() {
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
        stock.dateFormat = dateFormat;
        return stock;
    }

}
