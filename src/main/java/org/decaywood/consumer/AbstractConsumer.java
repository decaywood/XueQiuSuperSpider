package org.decaywood.consumer;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/30 22:22.
 */
public abstract class AbstractConsumer<T> implements Consumer<T>, Function<T, T> {

    protected abstract void consumLogic(T t) throws Exception;

    @Override
    public void accept(T t) {
        try {
            consumLogic(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T apply(T t) {
        accept(t);
        return t;
    }

}
