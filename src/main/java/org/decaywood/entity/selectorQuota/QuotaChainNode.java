package org.decaywood.entity.selectorQuota;

import org.decaywood.utils.DateParser;
import org.decaywood.utils.EmptyObject;

/**
 * @author: decaywood
 * @date: 2015/11/28 13:03
 */
public interface QuotaChainNode {

    default String getTimePrefix() {
        return "." + DateParser.getTimePrefix(true);
    }

    QuotaChainNode getNext();

    void setNext(QuotaChainNode next);

    String generateQuotaRequest();

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
