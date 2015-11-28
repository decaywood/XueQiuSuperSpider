package org.decaywood.entity.selectorQuota;

/**
 * @author: decaywood
 * @date: 2015/11/28 16:37
 */
public class MarketQuotationsQuota extends AbstractQuotaNode {

    private String current = "ALL"; //当前价
    private String pct =  "ALL"; //本日涨跌幅(%)
    private String pct5 = "ALL"; //前5日涨跌幅(%)
    private String pct10 = "ALL"; //前10日涨跌幅(%)
    private String pct20 = "ALL"; //前20日涨跌幅(%)
    private String pct1m = "ALL"; //近1月涨跌幅(%)
    private String chgpct = "ALL"; //本日振幅(%)
    private String chgpct5 = "ALL"; //前5日振幅(%)
    private String chgpct10 = "ALL"; //前10日振幅(%)
    private String chgpct20 = "ALL"; //前20日振幅(%)
    private String chgpct1m = "ALL"; //近1月振幅(%)
    private String volume = "ALL"; //本日成交量(万)
    private String volavg30 = "ALL"; //30日均量(万)
    private String amount = "ALL"; //成交额(万)
    private String tr = "ALL"; // 本日换手率(%)
    private String tr5 = "ALL"; //前5日换手率(%)
    private String tr10 = "ALL"; // 前10日换手率(%)
    private String tr20 = "ALL"; //前20日换手率(%)
    private String tr1m = "ALL"; //近1月换手率(%)

    public void setCurrent(String from, String to) {
        this.current = from + "_" + to;
    }

    public void setPct(String from, String to) {
        this.pct = from + "_" + to;
    }

    public void setPct5(String from, String to) {
        this.pct5 = from + "_" + to;
    }

    public void setPct10(String from, String to) {
        this.pct10 = from + "_" + to;
    }

    public void setPct20(String from, String to) {
        this.pct20 = from + "_" + to;
    }

    public void setPct1m(String from, String to) {
        this.pct1m = from + "_" + to;
    }

    public void setChgpct(String from, String to) {
        this.chgpct = from + "_" + to;
    }

    public void setChgpct5(String from, String to) {
        this.chgpct5 = from + "_" + to;
    }

    public void setChgpct10(String from, String to) {
        this.chgpct10 = from + "_" + to;
    }

    public void setChgpct20(String from, String to) {
        this.chgpct20 = from + "_" + to;
    }

    public void setChgpct1m(String from, String to) {
        this.chgpct1m = from + "_" + to;
    }

    public void setVolume(String from, String to) {
        this.volume = from + "_" + to;
    }

    public void setVolavg30(String from, String to) {
        this.volavg30 = from + "_" + to;
    }

    public void setAmount(String from, String to) {
        this.amount = from + "_" + to;
    }

    public void setTr(String from, String to) {
        this.tr = from + "_" + to;
    }

    public void setTr5(String from, String to) {
        this.tr5 = from + "_" + to;
    }

    public void setTr10(String from, String to) {
        this.tr10 = from + "_" + to;
    }

    public void setTr20(String from, String to) {
        this.tr20 = from + "_" + to;
    }

    public void setTr1m(String from, String to) {
        this.tr1m = from + "_" + to;
    }

    @Override
    StringBuilder builderSelf() {
        return null;
    }
}
