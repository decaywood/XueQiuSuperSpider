package org.decaywood.entity.selectorQuota;

/**
 * @author: decaywood
 * @date: 2015/11/28 12:48
 */
public class BasicQuota extends AbstractQuotaNode {


    private String mc = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;//总市值(亿)
    private String fmc = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//流通市值(亿)
    private String pettm = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//动态市盈率(倍)
    private String pelyr = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//静态市盈率(倍)
    private String eps = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//每股收益
    private String bps = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//每股净资产
    private String roediluted = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//净资产收益率
    private String netprofit = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//净利润(万)
    private String dy = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//股息
    private String pb = Integer.MIN_VALUE + "_" + Integer.MAX_VALUE;;//市净率(倍)



    public void setMc(String from, String to) {
        mc = from + "_" + to;
    }

    public void setFmc(String from, String to) {
        fmc = from + "_" + to;
    }

    public void setPettm(String from, String to) {
        pettm = from + "_" + to;
    }

    public void setPelyr(String from, String to) {
        pelyr = from + "_" + to;
    }

    public void setEps(String from, String to) {
        eps = from + "_" + to;
    }

    public void setBps(String from, String to) {
        bps = from + "_" + to;
    }

    public void setRoediluted(String from, String to) {
        roediluted = from + "_" + to;
    }

    public void setNetprofit(String from, String to) {
        netprofit = from + "_" + to;
    }

    public void setDy(String from, String to) {
        dy = from + "_" + to;
    }

    public void setPb(String from, String to) {
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
