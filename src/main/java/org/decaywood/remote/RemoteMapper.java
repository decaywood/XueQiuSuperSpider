package org.decaywood.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: decaywood
 * @date: 2015/12/13 16:28.
 */
public interface RemoteMapper<T, R> extends Remote {

    R apply(T t) throws RemoteException;

}
