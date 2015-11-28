package org.decaywood.entity.selectorQuota;

import org.decaywood.utils.EmptyObject;

import java.util.Calendar;

/**
 * @author: decaywood
 * @date: 2015/11/28 13:03
 */
public interface QuotaChainNode {

    default String getTimePrefix() {
        String time_prefix;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int diff = 1 - Calendar.JANUARY;
        int month = calendar.get(Calendar.MONTH) + diff;
        if(month > 9) month = 9;
        else if(month > 6) month = 6;
        else if(month > 3) month = 3;
        else {
            year--;
            month = 12;
        }
        int day;
        if(month == 3 || month == 12) day = 31;
        else day = 30;

        time_prefix = new StringBuilder(".")
                .append(year)
                .append(String.format("%02d", month))
                .append(String.format("%02d", day))
                .toString();
        return time_prefix;

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
