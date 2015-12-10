package org.decaywood;

import java.rmi.Remote;
import java.util.function.Consumer;

/**
 * @author: decaywood
 * @date: 2015/12/10 21:23.
 */
public interface Acceptor<T> extends Consumer<T>, Remote {
}
