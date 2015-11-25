package org.decaywood.mapper;

import org.decaywood.entity.Stock;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/25 11:14
 */
public interface StockFirst <R> extends Function<Stock, R> {

    @Override
    default <V> Function<Stock, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (Stock stock) -> after.apply(apply(stock));
    }

}
