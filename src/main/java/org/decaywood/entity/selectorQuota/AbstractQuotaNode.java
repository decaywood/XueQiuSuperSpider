package org.decaywood.entity.selectorQuota;

import org.decaywood.utils.EmptyObject;

/**
 * @author: decaywood
 * @date: 2015/11/28 15:36
 */

/**
 * 股票指标节点抽象类，负责对指标参数的组装
 */
public abstract class AbstractQuotaNode implements QuotaChainNode {

    private QuotaChainNode next = EmptyObject.emptyQuotaChainNode;

    @Override
    public QuotaChainNode getNext() {
        return next;
    }

    @Override
    public void setNext(QuotaChainNode next) {
        if(next == null) return;
        if(!end()) getNext().setNext(next);
        else this.next = next;
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
