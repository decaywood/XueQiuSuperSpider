package org.decaywood.entity;

import org.decaywood.utils.StringChecker;

import java.util.Date;
import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/27 0:29
 */
public class LongHuBangInfo implements DeepCopy<LongHuBangInfo> {

    private final Stock stock;
    private final Date date;

    private final List<BizsunitInfo> topBuyList;
    private final List<BizsunitInfo> topSaleList;

    public static class BizsunitInfo {
        private final String bizsunitcode; //营业部编号
        private final String bizsunitname; //营业部名称
        private final String buyamt; // 买入额度
        private final String saleamt; // 卖出额度
        private final String tradedate; //交易日期 yyyymmdd

        public BizsunitInfo(String bizsunitcode, String bizsunitname, String buyamt, String saleamt, String tradedate) {

            if(StringChecker.nullOrEmpty(bizsunitcode, bizsunitname, buyamt, saleamt, tradedate))
                throw new IllegalArgumentException();

            this.bizsunitcode = bizsunitcode;
            this.bizsunitname = bizsunitname;
            this.buyamt = buyamt;
            this.saleamt = saleamt;
            this.tradedate = tradedate;
        }
    }

    public LongHuBangInfo(Stock stock, Date date, List<BizsunitInfo> topBuyList, List<BizsunitInfo> topSaleList) {
        this.stock = stock;
        this.date = date;
        this.topBuyList = topBuyList;
        this.topSaleList = topSaleList;
    }

    public Stock getStock() {
        return stock;
    }

    public Date getDate() {
        return date;
    }

    public List<BizsunitInfo> getTopBuyList() {
        return topBuyList;
    }

    public List<BizsunitInfo> getTopSaleList() {
        return topSaleList;
    }

    @Override
    public LongHuBangInfo copy() {
        return null;
    }
}
