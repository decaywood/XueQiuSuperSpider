package org.decaywood.entity.selectorQuota;

/**
 * @author: decaywood
 * @date: 2015/11/28 12:48
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



    public void setMc(double from, double to) {
        mc = from + "_" + to;
    }

    public void setFmc(double from, double to) {
        fmc = from + "_" + to;
    }

    public void setPettm(double from, double to) {
        pettm = from + "_" + to;
    }

    public void setPelyr(double from, double to) {
        pelyr = from + "_" + to;
    }

    public void setEps(double from, double to) {
        eps = from + "_" + to;
    }

    public void setBps(double from, double to) {
        bps = from + "_" + to;
    }

    public void setRoediluted(double from, double to) {
        roediluted = from + "_" + to;
    }

    public void setNetprofit(double from, double to) {
        netprofit = from + "_" + to;
    }

    public void setDy(double from, double to) {
        dy = from + "_" + to;
    }

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
