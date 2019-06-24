/*
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DTOS.Movie;
import DTOS.MovieUserWatched;
import Exception.DaoException;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface MovieUserWatchedInterface {

    public MovieUserWatched watchMovie(String userName, int movieID) throws DaoException;
}
