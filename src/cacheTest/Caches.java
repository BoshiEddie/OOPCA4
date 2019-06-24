/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacheTest;

import BusinessObject.CacheGeneric;
import DTOS.Movie;
import java.util.List;

/**
 *
 * @author DELL
 */
public class Caches implements CacheInterface {

    private static CacheGeneric<String, Movie> cacheO = new CacheGeneric<>();
    private static CacheGeneric<String, List<Movie>> cacheL = new CacheGeneric<>();

    public void putCache(String key, Movie m) throws Exception {
        try {
            cacheO.put(key, m);
        } catch (Exception e) {
            throw e;
        }
    }

    public void putCache(String key, List<Movie> movies) throws Exception {
        try {
            cacheL.put(key, movies);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void getCacheByKey(String key) throws Exception {
        try {
            cacheO.get(key);
            cacheL.get(key);
        } catch (Exception e) {
            throw e;
        }
    }

    public void clearCache() {
        try {
            cacheO.clear();
            cacheL.clear();
        } catch (Exception e) {
            throw e;
        }
    }
}

