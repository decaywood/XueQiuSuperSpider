package org.decaywood.entity.selectorQuota;

/**
 * @author: decaywood
 * @date: 2015/11/28 12:48
 */

/**
 * 股票基本指标节点
 */
public class BasicQuota extends AbstractQuotaNode {


    private String mc = "ALL";//总市值(亿)
    private String fmc = "ALL";//流通市值(亿)
    private String pettm = "ALL";//动态市盈率(倍)
    private String pelyr = "ALL";//静态市盈率(倍)
    private String eps = "ALL";//每股收益
    private String bps = "ALL";//每股净资产
    private String roediluted = "ALL";//净资产收益率
    private String netprofit = "ALL";//净利润(万)
    private String dy = "ALL";//股息率(%)
    private String pb = "ALL";//市净率(倍)


    //设置总市值范围
    public void setMc(double from, double to) {
        mc = from + "_" + to;
    }

    //设置流通市值范围
    public void setFmc(double from, double to) {
        fmc = from + "_" + to;
    }
    //设置动态市盈率范围
    public void setPettm(double from, double to) {
        pettm = from + "_" + to;
    }
    //设置静态市盈率范围
    public void setPelyr(double from, double to) {
        pelyr = from + "_" + to;
    }
    //设置每股收益范围
    public void setEps(double from, double to) {
        eps = from + "_" + to;
    }
    //设置每股净资产范围
    public void setBps(double from, double to) {
        bps = from + "_" + to;
    }
    //设置净资产收益率范围
    public void setRoediluted(double from, double to) {
        roediluted = from + "_" + to;
    }
    //设置净利润范围
    public void setNetprofit(double from, double to) {
        netprofit = from + "_" + to;
    }
    //设置股息率范围
    public void setDy(double from, double to) {
        dy = from + "_" + to;
    }
    //设置市净率范围
    public void setPb(double from, double to) {
        pb = from + "_" + to;
    }


    @Override
    StringBuilder builderSelf() {
        StringBuilder builder = new StringBuilder();
        append(builder, "mc", mc);
        append(builder, "fmc", fmc);
        append(builder, "pettm", pettm);
        append(builder, "pelyr", pelyr);
        appendWithTimePrefix(builder, "eps", eps);
        appendWithTimePrefix(builder, "bps", bps);
        appendWithTimePrefix(builder, "roediluted", roediluted);
        appendWithTimePrefix(builder, "netprofit", netprofit);
        append(builder, "dy", dy);
        append(builder, "pb", pb);
        return builder;
    }
}
