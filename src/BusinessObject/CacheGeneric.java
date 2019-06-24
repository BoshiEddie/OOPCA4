/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author DELL
 */
public class CacheGeneric<K, V> {

    private Map<K, V> map;

    public CacheGeneric() {
        map = new HashMap<>();
    }

    synchronized public V get(K key) {
        return map.get(key);
    }

    synchronized public void put(K key, V value) {
        map.put(key, value);
    }

    public void clear() {
        map.clear();
    }

    public Set<K> getAllKey() {
        return map.keySet();
    }

    public boolean isContainsKey(K key) {
        if (map.containsKey(key)) {
            return true;
        }
        return false;
    }
}
