package org.decaywood.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: decaywood
 * @date: 2015/12/13 16:33.
 */
public interface RemoteAcceptor<T> extends Remote {

    void accept(T t) throws RemoteException;

}
