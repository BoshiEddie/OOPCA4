/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacheTest;

import DTOS.Movie;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface CacheInterface {

    //put cache Movie object 
    public void putCache(String key, Movie m) throws Exception;

    //put cache Movie List 
    public void putCache(String key, List<Movie> movies) throws Exception ;

    //get cache data by key
    public void getCacheByKey(String key)throws Exception;


    public void clearCache()throws Exception;

}
