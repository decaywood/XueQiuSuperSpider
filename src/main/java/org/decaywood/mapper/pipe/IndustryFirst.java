package org.decaywood.mapper.pipe;

import org.decaywood.entity.Industry;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/25 11:22
 */
public interface IndustryFirst <R> extends Function<Industry, R> {

    @Override
    default <V> Function<Industry, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (Industry industry) -> after.apply(apply(industry));
    }
}
