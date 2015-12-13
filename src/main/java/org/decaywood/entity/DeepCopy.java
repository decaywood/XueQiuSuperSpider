package org.decaywood.entity;

import java.io.Serializable;

/**
 * @author: decaywood
 * @date: 2015/11/24 19:52
 */
public interface DeepCopy <R> extends Serializable {

    R copy();

}
