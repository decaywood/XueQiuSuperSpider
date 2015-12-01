package org.decaywood.entity;

/**
 * @author: decaywood
 * @date: 2015/11/30 21:35.
 */
public class Entry<K, V> {

    private final K key;
    private final V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
