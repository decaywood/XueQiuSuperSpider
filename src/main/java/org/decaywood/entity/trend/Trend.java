package org.decaywood.entity.trend;

import org.decaywood.entity.DeepCopy;

import java.util.List;

/**
 * @author: decaywood
 * @date: 2015/11/26 9:57
 */


/**
 * 抽象类， 保存历史趋势
 */
public abstract class Trend <T, C> implements DeepCopy<C> {

    protected final List<T> history;

    public Trend(List<T> history) {
        if(history == null) throw new IllegalArgumentException();
        this.history = history;
    }

    public List<T> getHistory() {
        return history;
    }

}
