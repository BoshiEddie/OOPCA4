/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JunitTest;

import DAOS.MovieDaoInterface;
import DAOS.MySqlMovieDao;
import DTOS.Movie;
import Exception.DaoException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import BusinessObject.CacheGeneric;

import static org.junit.Assert.*;

/**
 *
 * @author DELL
 */
public class CacheGenericTest {

    public CacheGenericTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of findMovieById in caches.
     */
    @Test
    public void cacheTest() throws DaoException {
        System.out.println("findMovieById in caches");
        CacheGeneric caches = new CacheGeneric<>();
        String command = "FINDMOVIEBYID 15";
        Movie expResult = new Movie(15, "iron man 2", "Disney, marvel,Action, Adventure, Sci-Fi", "boshi pan",
                "124 min", "With the world now aware of his dual life as the armored superhero Iron Man, billionaire inventor Tony Stark faces pressure from the government, the press, and the public to share his technology with the military. Unwilling to let go of his invention, Stark, along with Pepper Potts, and James 'Rhodey' Rhodes at his side, must forge new alliances - and confront powerful enemies.",
                "", "null", "PG-13", "DVD", 2008, "Robert Downey Jr., Gwyneth Paltrow, Don Cheadle, Scarlett Johansson",
                3, "null", "null");
        caches.put(command, expResult);

        MovieDaoInterface instance = new MySqlMovieDao();
        Movie result = instance.findMovieById(15);

        assertEquals(caches.get(command), result);
    }

}
