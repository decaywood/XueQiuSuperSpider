package org.decaywood.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: decaywood
 * @date: 2015/12/13 16:35.
 */
public interface RemoteFilter<T> extends Remote {

    boolean test(T t) throws RemoteException;

}
