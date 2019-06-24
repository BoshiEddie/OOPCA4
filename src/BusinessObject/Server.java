package BusinessObject;

import DAOS.JoinedUserMovieDao;
import DAOS.JoinedUserMovieInterface;
import DAOS.MovieDaoInterface;
import DAOS.MovieUserWatchedInterface;
import DAOS.MySqlMovieDao;
import DAOS.MySqlMovieUserWatchedDao;
import DTOS.JoinedUserMovie;
import DTOS.Movie;
import DTOS.MovieUserWatched;
import Exception.DaoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.*;

public class Server {

    static MovieDaoInterface iMovieDao = new MySqlMovieDao();
    static MovieUserWatchedInterface iMovieWatchedDao = new MySqlMovieUserWatchedDao();
    static JoinedUserMovieInterface iJoinedUserMovieDao = new JoinedUserMovieDao();
    static Gson gson = new Gson();
    static CacheGeneric caches = new CacheGeneric<>();
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final Level LOG_LEVEL = Level.INFO;
    private FileHandler logFile = null;

    public static void main(String[] args) {
    	LOGGER.setLevel(LOG_LEVEL);
        Server server = new Server();
        server.start();
    }

    public void start() {

    	try {
    		
    		logFile = new FileHandler("Server.log",true);
    		
    	}catch(Exception e) {
    		
    		e.printStackTrace();
    		
    	}
    	
    	logFile.setFormatter(new SimpleFormatter());
    	LOGGER.addHandler(logFile);
    	
    	LOGGER.info("Server Starting.....");
    	
    	try {
        	
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            LOGGER.info("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true) // loop continuously to accept new client connections
            {
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection, 
                // and open a new socket to communicate with the client
                clientNumber++;

                LOGGER.info("Server: Client " + clientNumber + " has connected.");

                LOGGER.info("Server: Port# of remote client: " + socket.getPort());
                
                LOGGER.info("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, iMovieDao, iMovieWatchedDao,iJoinedUserMovieDao,clientNumber)); // create a new ClientHandler for the client,
                t.start();  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                
                System.out.println("Server: Listening for further connections...");
                
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
            LOGGER.warning("IOException caught");
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable // each ClientHandler communicates with one Client
    {

        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;

        public ClientHandler(Socket clientSocket,MovieDaoInterface iMovieDao, MovieUserWatchedInterface iMovieWatchedDao, JoinedUserMovieInterface iJoinedUserMovieDao,int clientNumber) {
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing 

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
        	
            String command = "";
            String message;
            String emptyMessage = "There is no moive you searched, please check again.";

            try {
                while ((command = socketReader.readLine()) != null) {
                    LOGGER.info("Server: (ClientHandler): Read command from client " + clientNumber + ": " + command);

                    String[] details = splitCommand(command);
                    String subCommand = details[0].toUpperCase();

                    List<Movie> movies = new ArrayList<>();

                    Movie m = new Movie();
                    MovieUserWatched movieWa = new MovieUserWatched();

                    JoinedUserMovie jum = new JoinedUserMovie();

                    String cacheKey = command.toUpperCase();
                    switch (subCommand) {
                        case "TESTMOVIES":
                            if (!caches.isContainsKey(cacheKey)) {
                                movies = testMovies();
                                if (movies.isEmpty()) {
                                    message = emptyMessage;
                                } else {
                                    message = converToJson(movies);
                                }
                                caches.put(cacheKey, movies);
                                //System.out.println("insert " + cacheKey);
                            } else {
                                movies = (List<Movie>) caches.get(cacheKey);
                                message = converToJson(movies);
                                //System.out.println("extract " + cacheKey);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYID":
                            if (!caches.isContainsKey(cacheKey)) {
                                m = findMovieById(Integer.parseInt(details[1]));
                                if (m == null) {
                                    message = emptyMessage;
                                } else {
                                    message = toJson(m);
                                }
                                caches.put(cacheKey, m);
                                //System.out.println("insert " + cacheKey);
                            } else {
                                m = (Movie) caches.get(cacheKey);
                                message = toJson(m);
                                //System.out.println("extract " + cacheKey);
                            }
                            socketWriter.println(message);
                            break;
                            
                        case "FINDMOVIEBYGENRE":
                        	if(!caches.isContainsKey(cacheKey)) {
                        		movies = findMovieByGenre(details[1]);
                        		if(movies.isEmpty()) {
                        			message = emptyMessage;
                        		}else {
                        			message = converToJson(movies);
                        		}
                        		caches.put(cacheKey, movies);
                        	}else {
                        		movies = (List<Movie>)caches.get(cacheKey);
                        		message = converToJson(movies);
                        	}
                        	
                        	socketWriter.println(message);
                        	break;

                        case "FINDMOVIEBYTITLE":
                            if (!caches.isContainsKey(cacheKey)) {
                                movies = findMovieByTitle(details[1]);
                                if (movies.isEmpty()) {
                                    message = emptyMessage;
                                } else {
                                    message = converToJson(movies);
                                }
                                caches.put(cacheKey, movies);
                                //System.out.println("insert " + cacheKey);
                            } else {
                                movies = (List<Movie>) caches.get(cacheKey);
                                message = converToJson(movies);
                                //System.out.println("extract " + cacheKey);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYYEAR":
                            if (!caches.isContainsKey(cacheKey)) {
                                movies = findMovieByYear(Integer.parseInt(details[1]));
                                if (movies.isEmpty()) {
                                    message = emptyMessage;
                                } else {
                                    message = converToJson(movies);
                                }
                                caches.put(cacheKey, movies);
                                //System.out.println("insert " + cacheKey);
                            } else {
                                movies = (List<Movie>) caches.get(cacheKey);
                                message = converToJson(movies);
                                //System.out.println("extract " + cacheKey);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEBYDIRECTOR":
                            if (!caches.isContainsKey(cacheKey)) {
                                movies = findMovieByDirector(details[1]);
                                if (movies.isEmpty()) {
                                    message = emptyMessage;
                                } else {
                                    message = converToJson(movies);
                                }
                                caches.put(cacheKey, movies);
                                //System.out.println("insert " + cacheKey);
                            } else {
                                movies = (List<Movie>) caches.get(cacheKey);
                                message = converToJson(movies);
                                //System.out.println("extract " + cacheKey);
                            }
                            socketWriter.println(message);
                            break;

                        case "TOPTENMOVIES":
                            if (!caches.isContainsKey(cacheKey)) {
                                movies = topTenMovies();
                                if (movies.isEmpty()) {
                                    message = emptyMessage;
                                } else {
                                    message = converToJson(movies);
                                }
                                caches.put(cacheKey, movies);
                                //System.out.println("insert " + cacheKey);
                            } else {
                                movies = (List<Movie>) caches.get(cacheKey);
                                message = converToJson(movies);
                                //System.out.println("extract " + cacheKey);
                            }
                            socketWriter.println(message);
                            break;

                        case "FINDMOVIEWATCHEDBYUSERNAME":
                            if (!caches.isContainsKey(cacheKey)) {
                                jum = findMovieWatchedByUserName(details[1]);
                                if (jum == null) {
                                    message = emptyMessage;
                                } else {
                                    message = movieWatchedByUserNameToJson(jum);
                                }
                                caches.put(cacheKey, jum);
                                //System.out.println("insert " + cacheKey);
                            } else {
                                jum = (JoinedUserMovie) caches.get(cacheKey);
                                message = movieWatchedByUserNameToJson(jum);
                                //System.out.println("extract " + cacheKey);
                            }
                            socketWriter.println(message);
                            break;

                        case "INSERTMOVIE":
                            m = insertMovie(details[1], details[2], details[3], details[4], details[5], details[6], details[7],
                                    details[8], details[9], Integer.parseInt(details[10]), details[11], Integer.parseInt(details[12]), details[13], details[14]);
                            if (m == null) {
                                message = emptyMessage;
                            } else {
                                message = toJson(m);
                            }
                            socketWriter.println(message);
                            caches.clear();
                            break;

                        case "DELETEMOVIE":
                            message = deleteMovie(Integer.parseInt(details[1]));
                            socketWriter.println(message);
                            caches.clear();
                            break;

                        case "UPDATEMOVIE":
                            m = updateMovie(Integer.parseInt(details[1]), details[2], details[3]);
                            message = toJson(m);
                            socketWriter.println(message);
                            caches.clear();
                            break;

                        case "MOVIEWATCH":
                            movieWa = movieWatch(details[2],Integer.parseInt(details[1]));
                            message = movieWatchedToJson(movieWa);
                            socketWriter.println(message);
                            break;

                        case "RECOMMANDMOVIE":
                            movies = recommandMovie(details[1]);
                            if (movies.isEmpty()) {
                                message = emptyMessage;
                            } else {
                                message = converToJson(movies);
                            }
                            socketWriter.println(message);
                            break;

                        case "Q":
                            socketWriter.println("Bye");
                            break;

                        default:
                            socketWriter.println("I'm sorry I don't understand :(");
                            break;
                    }
                }
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                LOGGER.warning("IOException caught");
            } catch (DaoException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            LOGGER.info("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }
    }

    public static String[] splitCommand(String command) {
        String[] details = null;

        if (command.contains(" ")) {
            String[] lineWords = command.split(" ");
            String subCommand = lineWords[0].toUpperCase();

            switch (subCommand) {
                case "DELETEMOVIE":
                case "FINDMOVIEBYID":
                case "FINDMOVIEBYYEAR":
                case "FINDMOVIEBYTITLE":
                case "FINDMOVIEBYDIRECTOR":
                case "FINDMOVIEWATCHEDBYUSERNAME":
                case "RECOMMANDMOVIE":
                case "FINDMOVIEBYGENRE":
                    String str = "";
                    for (int i = 1; i < lineWords.length; i++) {
                        str += lineWords[i];
                        if (i < lineWords.length - 1) {
                            str += " ";
                        }
                    }
                    details = new String[2];
                    details[0] = subCommand;
                    details[1] = str;
                    break;

                case "UPDATEMOVIE":
                    String str1 = "";
                    for (int i = 3; i < lineWords.length; i++) {
                        str1 += lineWords[i];
                        if (i < lineWords.length - 1) {
                            str1 += " ";
                        }
                    }
                    details = new String[4];
                    details[0] = subCommand;
                    details[1] = lineWords[1];
                    details[2] = lineWords[2];
                    details[3] = str1;
                    break;

                case "INSERTMOVIE":
                    details = new String[15];
                    details[0] = subCommand;
                    for (int i = 1; i < lineWords.length; i++) {
                        details[i] = lineWords[i];
                    }
                    break;

                case "MOVIEWATCH":
                    details = new String[3];
                    details[0] = subCommand;
                    for (int i = 1; i < lineWords.length; i++) {
                        details[i] = lineWords[i];
                    }
                    break;
                    
                default:
                	details = lineWords;
                	
            }
        } else {
            details = new String[1];
            details[0] = command;
        }

        return details;

    }

    public synchronized List<Movie> testMovies() throws DaoException {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.testMovies();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public synchronized Movie findMovieById(int id) {
        Movie m = new Movie();
        try {
            m = iMovieDao.findMovieById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return m;
    }

    public synchronized List<Movie> findMovieByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.findMovieByTitle(title);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public synchronized List<Movie> topTenMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.topTenMovies();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }
    
    public List<Movie> findMovieByGenre(String genre){
    	
    	List<Movie> movies = new ArrayList<>();
    	
    	try {
    		
    		movies = iMovieDao.findMovieByGenre(genre);
    		
    	}catch(DaoException e) {
    		
    		e.printStackTrace();
    		
    	}
    	
    	return movies;
    	
    }

    public synchronized Movie updateMovie(int id, String str1, String str2) {
    	Movie m = new Movie();
    	try {
              m = iMovieDao.updateMovie(id, str1, str2);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return m;
    }

    public synchronized String deleteMovie(int id) {
        String message = "";
        try {
            if (iMovieDao.findMovieById(id) == null) {
                message = "There is no movie you deleted, please check ID again";
            } else {
                message = iMovieDao.deleteMovie(id);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return message;
    }

//    public synchronized Movie insertNewMovie() {
//        Movie m = new Movie();
//        try {
//            m = iMovieDao.insertNewMovie();
//
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//        return m;
//    }
    public synchronized Movie insertMovie(String title, String genre, String director, String runtime,
            String plot, String location, String poster, String rating, String format,
            int year, String starring, int copies, String barcode, String userRating) {
        Movie m = new Movie();
        try {
            m = iMovieDao.insertMovie(title, genre, director, runtime, plot, location, poster, rating, format, year, starring, copies, barcode, userRating);

        } catch (DaoException e) {
            e.printStackTrace();
        }
        return m;
    }

    public synchronized List<Movie> findMovieByYear(int year) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.findMovieByYear(year);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public synchronized List<Movie> findMovieByDirector(String directorName) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = iMovieDao.findMovieByDirector(directorName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static String converToJson(List<Movie> movies) {
        String jsonString = "[ ";
        int i = 1;
        for (Movie movie : movies) {
            jsonString += toJson(movie);

            if (i < movies.size()) {
                jsonString += ",";
                i++;
            } else {
                jsonString += " ";
            }

        }
        jsonString += "]";
        		
        return jsonString;
    }

    public static String toJson(Movie m) {
        return gson.toJson(m);
    }

    public synchronized static JoinedUserMovie findMovieWatchedByUserName(String userName) {
        JoinedUserMovie jum = new JoinedUserMovie();
        try {
            jum = iJoinedUserMovieDao.findMovieWatchedByUserName(userName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return jum;
    }

    public synchronized static MovieUserWatched movieWatch(String userName, int movieID) {
        MovieUserWatched movieWa = new MovieUserWatched();
        try {
            movieWa = iMovieWatchedDao.watchMovie(userName, movieID);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return movieWa;
    }

    public static String movieWatchedToJson(MovieUserWatched muw) {
        return gson.toJson(muw);
    }

    public static String movieWatchedByUserNameToJson(JoinedUserMovie jum) {
        String jsonString = "[ ";
        int i = 1;
        for (Movie movie : jum.getMovies()) {
            jsonString += toJson(movie);

            if (i < jum.getMovies().size()) {
                jsonString += ",";
                i++;
            } else {
                jsonString += " ";
            }

        }
        jsonString += "]";

        return jsonString;
    }

    public synchronized static List<Movie> recommandMovie(String userName) {
        List<Movie> movies = new ArrayList<>();
        List<Movie> recommandMovies = new ArrayList<>();
        try {
            movies = iJoinedUserMovieDao.findMovieWatchedByUserName(userName).getMovies();

            //fetch movie directors
            List<String> directors = new ArrayList<>();
            for (Movie m1 : movies) {
                if (m1.getDirector().contains(",")) {
                    String[] direc = m1.getDirector().split(",");
                    for (String s : direc) {
                        directors.add(s);
                    }
                } else {
                    directors.add(m1.getDirector());
                }
            }

            //get frequence of each director
            Map<String, Integer> mapD = new HashMap<>();
            for (String d : directors) {
                if (mapD.containsKey(d)) {
                    mapD.put(d, mapD.get(d).intValue() + 1);
                } else {
                    mapD.put(d, new Integer(1));
                }
            }
            //get max fre of director
            String maxDirec = null;
            int max = 0;
            Iterator<String> iter = mapD.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                Integer fre = mapD.get(key);
                if (max > fre) {
                    max = max;
                } else {
                    max = fre;
                    maxDirec = key;
                }
            }

            //fetch movie genre
            List<String> genres = new ArrayList<>();
            for (Movie m1 : movies) {
                if (m1.getGenre().contains(",")) {
                    String[] genre = m1.getGenre().split(",");
                    for (String s : genre) {
                        genres.add(s);
                    }
                } else {
                    genres.add(m1.getGenre());
                }
            }

            //get frequence of each genre
            Map<String, Integer> mapG = new HashMap<>();
            for (String g : genres) {
                if (mapG.containsKey(g)) {
                    mapG.put(g, mapG.get(g).intValue() + 1);
                } else {
                    mapG.put(g, 1);
                }
            }
            //get max fre of genre
            String maxGenre = null;
            int maxG = 0;
            Iterator<String> iterG = mapG.keySet().iterator();
            while (iterG.hasNext()) {
                String key = iterG.next();
                Integer fre = mapG.get(key);
                if (maxG > fre) {
                    maxG = maxG;
                } else {
                    maxG = fre;
                    maxGenre = key;
                }
            }

            //fetch movie actor
            List<String> actors = new ArrayList<>();
            for (Movie m1 : movies) {
                if (m1.getStarring().contains(",")) {
                    String[] act = m1.getStarring().split(",");
                    for (String s : act) {
                        actors.add(s);
                    }
                } else {
                    actors.add(m1.getStarring());
                }
            }

            //get frequence of each actor
            Map<String, Integer> mapA = new HashMap<>();
            for (String a : actors) {
                if (mapA.containsKey(a)) {
                    mapA.put(a, mapA.get(a).intValue() + 1);
                } else {
                     mapA.put(a, 1);
                }
            }
            //get max fre of actor
            String maxActor = null;
            int maxA = 0;
            Iterator<String> iterA = mapA.keySet().iterator();
            while (iterA.hasNext()) {
                String key = iterA.next();
                Integer fre = mapA.get(key);
                if (maxA > fre) {
                    maxA = maxA;
                } else {
                    maxA = fre;
                    maxActor = key;
                }
            }
            recommandMovies = iMovieDao.recommandedMovies(maxDirec, maxGenre, maxActor);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return recommandMovies;
    }
}
