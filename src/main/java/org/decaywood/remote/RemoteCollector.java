package org.decaywood.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: decaywood
 * @date: 2015/12/13 16:26.
 */
public interface RemoteCollector<T> extends Remote {

    T get() throws RemoteException;

}
