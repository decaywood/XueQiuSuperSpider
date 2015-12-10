package org.decaywood.entity.selectorQuota;

/**
 * @author: decaywood
 * @date: 2015/11/28 15:17
 */

/**
 * 雪球指标节点
 */
public class XueQiuQuota extends AbstractQuotaNode {


    private String follow = "ALL"; //累计关注人数
    private String tweet = "ALL"; //累计讨论次数
    private String deal = "ALL"; //累计交易分享数
    private String follow7d = "ALL"; //一周新增关注
    private String tweet7d = "ALL"; //一周新增讨论数
    private String deal7d = "ALL"; //一周新增交易分享数
    private String follow7dpct = "ALL"; //一周关注增长率(%)
    private String tweet7dpct = "ALL"; //一周讨论增长率(%)
    private String deal7dpct = "ALL"; //一周交易分享增长率(%)

    private QuotaChainNode next;

    //设置累计关注人数范围
    public void setFollow(double from, double to) {
        this.follow = from + "_" + to;
    }

    //设置累计讨论次数范围
    public void setTweet(double from, double to) {
        this.tweet = from + "_" + to;
    }

    //设置累计交易分享数范围
    public void setDeal(double from, double to) {
        this.deal = from + "_" + to;
    }

    //设置一周新增关注范围
    public void setFollow7d(double from, double to) {
        this.follow7d = from + "_" + to;
    }

    //设置一周新增讨论数范围
    public void setTweet7d(double from, double to) {
        this.tweet7d = from + "_" + to;
    }

    //设置一周新增交易分享数范围
    public void setDeal7d(double from, double to) {
        this.deal7d = from + "_" + to;
    }

    //设置一周关注增长率范围
    public void setFollow7dpct(double from, double to) {
        this.follow7dpct = from + "_" + to;
    }

    //设置一周讨论增长率范围
    public void setTweet7dpct(double from, double to) {
        this.tweet7dpct = from + "_" + to;
    }

    //设置一周交易分享增长率范围
    public void setDeal7dpct(double from, double to) {
        this.deal7dpct = from + "_" + to;
    }



    @Override
    StringBuilder builderSelf() {
        StringBuilder builder = new StringBuilder();

        append(builder, "follow", follow);
        append(builder, "tweet", tweet);
        append(builder, "deal", deal);

        append(builder, "follow7d", follow7d);
        append(builder, "tweet7d", tweet7d);
        append(builder, "deal7d", deal7d);

        append(builder, "follow7dpct", follow7dpct);
        append(builder, "tweet7dpct", tweet7dpct);
        append(builder, "deal7dpct", deal7dpct);

        return builder;
    }
}
