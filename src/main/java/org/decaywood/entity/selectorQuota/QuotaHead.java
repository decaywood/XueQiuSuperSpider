package org.decaywood.entity.selectorQuota;

/**
 * @author: decaywood
 * @date: 2015/11/28 12:48
 */
public class QuotaHead extends AbstractQuotaNode {

    private String category = "SH";
    private String exchange = ""; //市场
    private String areacode = ""; //地域
    private String indcode = ""; //板块代码
    private String orderby = "symbol"; //排序方式
    private String order = "desc";
    private String page = "1";


    public void setCategory(String category) {
        this.category = category;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public void setIndcode(String indcode) {
        this.indcode = indcode;
    }




    @Override
    StringBuilder builderSelf() {
        StringBuilder builder = new StringBuilder("?");
        append(builder, "category", category);
        append(builder, "exchange", exchange);
        append(builder, "areacode", areacode);
        append(builder, "indcode", indcode);
        append(builder, "orderby", orderby);
        append(builder, "order", order);
        append(builder, "page", page);

        return builder;
    }


}
