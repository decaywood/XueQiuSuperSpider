package org.decaywood;

import java.rmi.Remote;
import java.util.function.Function;

/**
 * @author: decaywood
 * @date: 2015/12/10 21:18.
 */
public interface Mapper<T, R> extends Function<T, R>, Remote {
}
