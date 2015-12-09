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
