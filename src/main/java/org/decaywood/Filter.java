package org.decaywood;

import java.rmi.Remote;
import java.util.function.Predicate;

/**
 * @author: decaywood
 * @date: 2015/12/10 21:26.
 */
public interface Filter<T> extends Predicate<T>, Remote {
}
