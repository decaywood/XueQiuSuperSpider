package org.decaywood.mapper;

import org.decaywood.entity.Cube;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/11/25 23:10.
 */
public interface CubeFirst <R> extends Function<Cube, R> {

    @Override
    default <V> Function<Cube, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (Cube industry) -> after.apply(apply(industry));
    }

}
