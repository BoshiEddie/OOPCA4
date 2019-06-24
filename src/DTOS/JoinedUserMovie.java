/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOS;

import java.util.List;

/**
 *
 * @author DELL
 */
public class JoinedUserMovie {

    private String userName;
    private List<Movie> movies;

    public JoinedUserMovie(){}
    
    public JoinedUserMovie(String userName, List<Movie> movies) {
        this.userName = userName;
        this.movies = movies;
    }

    public String getUserName() {
        return userName;
    }

    public List<Movie> getMovies() {
        return movies;
    }
    
}
