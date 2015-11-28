package org.decaywood.entity.selectorQuota;

import org.decaywood.utils.EmptyObject;

/**
 * @author: decaywood
 * @date: 2015/11/28 15:36
 */
public abstract class AbstractQuotaNode implements QuotaChainNode {

    private QuotaChainNode next = EmptyObject.emptyQuotaChainNode;

    @Override
    public QuotaChainNode getNext() {
        return next;
    }

    @Override
    public void setNext(QuotaChainNode next) {
        this.next = next == null ? EmptyObject.emptyQuotaChainNode : next;
    }

    @Override
    public String generateQuotaRequest() {
        StringBuilder builder = builderSelf();
        if(!this.end())
            builder.append(this.getNext().generateQuotaRequest());
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    abstract StringBuilder builderSelf();
}
