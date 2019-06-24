package BusinessObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import DTOS.JoinedUserMovie;
import DTOS.Movie;
import DTOS.MovieUserWatched;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
    	
    	Gson gson = new Gson();
    	
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket, and open new socket

            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort());

            System.out.println("Client: This Client is running and has connected to the server");

            System.out.println("If you want to insert a new movie ,please use space to indicate table colunm is finished except last one"
                    + "and if the column is empty please use null instead");
            
        	System.out.println("Please enter your user name.");
        	
        	String name = in.nextLine();
            
            String message = "please enter command:";
            System.out.print(message);
            String command;

            OutputStream os = socket.getOutputStream();

            PrintWriter socketWriter = new PrintWriter(os, true);

            Scanner socketReader = new Scanner(socket.getInputStream());
            labelB:
            while (in.hasNextLine()) {
                command = in.nextLine();  // read a command from the user
                
                if(command.equalsIgnoreCase("recommandmovie") || command.toLowerCase().startsWith("moviewatch")
                		||command.equalsIgnoreCase("findmoviewatchedbyusername")
                		) {
                	command += " " + name;
                }
                
                String[] lineWords = command.split(" ");
                String subCommand = lineWords[0].toUpperCase();

                String feedback;
                socketWriter.println(command);
                labelA:
                switch (subCommand) {
	                case "FINDMOVIEBYID"://movie
	                case "UPDATEMOVIE"://movie
	                case "INSERTMOVIE":
	                	
	                	feedback = socketReader.nextLine();
	                	
	                	Movie m = gson.fromJson(feedback, Movie.class);
						
						System.out.println(m.getTitle());
						
						break;
	                	
	                case "FINDMOVIEWATCHEDBYUSERNAME"://jionedMovieUserWatched
                    case "TESTMOVIES"://list of movies
                    case "FINDMOVIEBYTITLE"://list of movies
                    case "FINDMOVIEBYYEAR"://list of movies
                    case "FINDMOVIEBYDIRECTOR"://list of movies
                    case "FINDMOVIEBYGENRE"://list of movies
                    case "TOPTENMOVIES"://list of movies
                    case "RECOMMANDMOVIE"://list of movies
                    	List<Movie> movies;

                    	feedback = socketReader.nextLine();
                    	
						Type listType = new TypeToken<List<Movie>>() {}.getType();
						
						movies = gson.fromJson(feedback, listType);
						
						movies.forEach(x -> System.out.println(x.getTitle()));
                    
						break;
 
                    case "MOVIEWATCH"://
                    	feedback = socketReader.nextLine();
                    	
                    	MovieUserWatched jum = gson.fromJson(feedback, MovieUserWatched.class);
                    	
                    	System.out.println(jum.getMovieId());
                    	
                    	break;
                    	
                    	
                    case "DELETEMOVIE":
                        feedback = socketReader.nextLine();
                        System.out.println("Message:" + feedback);
                        break;

                    case "Q":
                    	feedback = socketReader.nextLine();
                        System.out.println("Message:" + feedback);
                        break labelB;
                    default:
                        feedback = socketReader.nextLine();
                        System.out.println("Message:" + feedback);
                        break labelA;

                }
                System.out.print(message);
            }
            socketWriter.close();
            socketReader.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
    }
}
