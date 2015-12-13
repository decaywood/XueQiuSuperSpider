package org.decaywood.entity.selectorQuota;

import org.decaywood.utils.DateParser;
import org.decaywood.utils.EmptyObject;

import java.io.Serializable;

/**
 * @author: decaywood
 * @date: 2015/11/28 13:03
 */

/**
 * 股票选择指标统一接口
 */
public interface QuotaChainNode extends Serializable {


    //获取下一个指标节点
    QuotaChainNode getNext();
    //设置指标节点
    void setNext(QuotaChainNode next);
    //生成请求字符串
    String generateQuotaRequest();


    //------------------------------------ 以下为系统方法 ---------------------------------------------


    default String getTimePrefix() {
        return "." + DateParser.getTimePrefix(true);
    }

    default boolean end() {
        return getNext() == EmptyObject.emptyQuotaChainNode;
    }

    default QuotaChainNode append(StringBuilder builder, String paramName, String param) {
        if("ALL".equals(param)) return this;
        builder.append(paramName).append("=").append(param).append("&");
        return this;
    }

    default QuotaChainNode appendWithTimePrefix(StringBuilder builder, String paramName, String param) {
        if("ALL".equals(param)) return this;
        builder.append(paramName.concat(getTimePrefix())).append("=").append(param).append("&");
        return this;
    }

}
