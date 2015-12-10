package org.decaywood.entity.selectorQuota;

/**
 * @author: decaywood
 * @date: 2015/11/28 16:37
 */

/**
 * 市场指标节点
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

    //设置当前价范围
    public void setCurrent(double from, double to) {
        this.current = from + "_" + to;
    }

    //设置本日涨跌幅范围
    public void setPct(double from, double to) {
        this.pct = from + "_" + to;
    }

    //设置前5日涨跌幅范围
    public void setPct5(double from, double to) {
        this.pct5 = from + "_" + to;
    }

    //设置前10日涨跌幅范围
    public void setPct10(double from, double to) {
        this.pct10 = from + "_" + to;
    }

    //设置前20日涨跌幅范围
    public void setPct20(double from, double to) {
        this.pct20 = from + "_" + to;
    }

    //设置近1月涨跌幅范围
    public void setPct1m(double from, double to) {
        this.pct1m = from + "_" + to;
    }

    //设置本日振幅范围
    public void setChgpct(double from, double to) {
        this.chgpct = from + "_" + to;
    }

    //设置前5日振幅范围
    public void setChgpct5(double from, double to) {
        this.chgpct5 = from + "_" + to;
    }

    //设置前10日振幅范围
    public void setChgpct10(double from, double to) {
        this.chgpct10 = from + "_" + to;
    }

    //设置前20日振幅范围
    public void setChgpct20(double from, double to) {
        this.chgpct20 = from + "_" + to;
    }

    //设置前1月振幅范围
    public void setChgpct1m(double from, double to) {
        this.chgpct1m = from + "_" + to;
    }

    //设置本日成交量范围
    public void setVolume(double from, double to) {
        this.volume = from + "_" + to;
    }

    //设置前30日均量范围
    public void setVolavg30(double from, double to) {
        this.volavg30 = from + "_" + to;
    }

    //设置成交额范围
    public void setAmount(double from, double to) {
        this.amount = from + "_" + to;
    }

    //设置本日换手率范围
    public void setTr(double from, double to) {
        this.tr = from + "_" + to;
    }

    //设置前5日换手率范围
    public void setTr5(double from, double to) {
        this.tr5 = from + "_" + to;
    }

    //设置前10日换手率范围
    public void setTr10(double from, double to) {
        this.tr10 = from + "_" + to;
    }

    //设置前20日换手率范围
    public void setTr20(double from, double to) {
        this.tr20 = from + "_" + to;
    }

    //设置前1月换手率范围
    public void setTr1m(double from, double to) {
        this.tr1m = from + "_" + to;
    }



    @Override
    StringBuilder builderSelf() {
        StringBuilder builder = new StringBuilder();
        append(builder, "current", current);
        append(builder, "pct", pct);
        append(builder, "pct5", pct5);
        append(builder, "pct10", pct10);
        append(builder, "pct20", pct20);
        append(builder, "pct1m", pct1m);
        append(builder, "chgpct", chgpct);
        append(builder, "chgpct5", chgpct5);
        append(builder, "chgpct10", chgpct10);
        append(builder, "chgpct20", chgpct20);
        append(builder, "chgpct1m", chgpct1m);
        append(builder, "volume", volume);
        append(builder, "volavg30", volavg30);
        append(builder, "amount", amount);
        append(builder, "tr", tr);
        append(builder, "tr5", tr5);
        append(builder, "tr10", tr10);
        append(builder, "tr20", tr20);
        append(builder, "tr1m", tr1m);
        return builder;
    }
}
