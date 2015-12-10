package org.decaywood;

import java.rmi.Remote;
import java.util.function.Supplier;

/**
 * @author: decaywood
 * @date: 2015/12/10 21:21.
 */
public interface Collector<T> extends Supplier<T>, Remote {
}
