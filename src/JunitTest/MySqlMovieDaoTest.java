/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JunitTest;

import DTOS.Movie;
import DAOS.MovieDaoInterface;
import DAOS.MySqlMovieDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL and Eddie
 */
public class MySqlMovieDaoTest {

    public MySqlMovieDaoTest() {
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
     * Test of findMovieById method, of class MySqlMovieDao.
     */
    @Test
    public void testFindMovieById() throws Exception {
        System.out.println("findMovieById");
        int id = 15;
        Movie expResult = new Movie(15, "iron man 2", "Disney, marvel,Action, Adventure, Sci-Fi", "boshi pan",
                "124 min", "With the world now aware of his dual life as the armored superhero Iron Man, billionaire inventor Tony Stark faces pressure from the government, the press, and the public to share his technology with the military. Unwilling to let go of his invention, Stark, along with Pepper Potts, and James 'Rhodey' Rhodes at his side, must forge new alliances - and confront powerful enemies.",
                "", "null", "PG-13", "DVD", 2008, "Robert Downey Jr., Gwyneth Paltrow, Don Cheadle, Scarlett Johansson",
                3, "null", "null");
        MovieDaoInterface instance = new MySqlMovieDao();
        Movie result = instance.findMovieById(id);
        assertEquals(expResult, result);
    }
    /**
     * Test of findMovieByGenre method, of Class MySqlMovieDao.
     * **/
    
    @Test
    public void testFindMovieByGenre() throws Exception{
    	System.out.println("findmoviebygenre");
    	List<Movie> expresult = new ArrayList<>();
    	String genre = "action";
    	MovieDaoInterface instance = new MySqlMovieDao();
    	Movie expresult1 = new Movie(14, "iron man", "Marvel, Action, Adventure, Sci-Fi","Jon Favreau",
    			"126 min", "Tony Stark. Genius, billionaire, playboy, philanthropist. Son of legendary inventor and weapons contractor Howard Stark. When Tony Stark is assigned to give a weapons presentation to an Iraqi unit led by Lt. Col. James Rhodes, he's given a ride on enemy lines. That ride ends badly when Stark's Humvee that he's riding in is attacked by enemy combatants. He survives - barely - with a chest full of shrapnel and a car battery attached to his heart. In order to survive he comes up with a way to miniaturize the battery and figures out that the battery can power something else. Thus Iron Man is born. He uses the primitive device to escape from the cave in Iraq. Once back home, he then begins work on perfecting the Iron Man suit. But the man who was put in charge of Stark Industries has plans of his own to take over Tony's technology for other matters.",
    			"","null","PG-13", "DVD", 2008, "Robert Downey Jr., Terrence Howard, Jeff Bridges, Gwyneth Paltrow",
    			3, "null","null");    	
    	Movie expresult2 = new Movie(15, "iron man 2", "Disney, marvel,Action, Adventure, Sci-Fi", "boshi pan",
                "124 min", "With the world now aware of his dual life as the armored superhero Iron Man, billionaire inventor Tony Stark faces pressure from the government, the press, and the public to share his technology with the military. Unwilling to let go of his invention, Stark, along with Pepper Potts, and James 'Rhodey' Rhodes at his side, must forge new alliances - and confront powerful enemies.",
                "", "null", "PG-13", "DVD", 2008, "Robert Downey Jr., Gwyneth Paltrow, Don Cheadle, Scarlett Johansson",
                3, "null", "null");    	
    	Movie expresult3 = new Movie(16,"x men first class", "Action, Adventure, Sci-Fi","Matthew Vaughn",
    			"132 min", "Before Charles Xavier and Erik Lensherr took the names Professor X and Magneto, they were two young men discovering their powers for the first time. Before they were archenemies, they were closest of friends, working together, with other Mutants (some familiar, some new), to stop the greatest threat the world has ever known. In the process, a rift between them opened, which began the eternal war between Magneto's Brotherhood and Professor X's X-MEN.",
    			"", "null","PG-13", "DVD", 2011, "James McAvoy, Laurence Belcher, Michael Fassbender, Bill Milner",
    			1, "null","null");    	
    	Movie expresult4 = new Movie(17, "the wolverine", "Action, Adventure, Sci-Fi", "James Mangold",
    			"126 min", "In modern day Japan, Wolverine is out of his depth in an unknown world as he faces his ultimate nemesis in a life-or-death battle that will leave him forever changed. Vulnerable for the first time and pushed to his physical and emotional limits, he confronts not only lethal samurai steel but also his inner struggle against his own immortality, emerging more powerful than we have ever seen him before.",
    			"", "null", "PG-13","DVD", 2013, "Hugh Jackman, Tao Okamoto, Rila Fukushima, Hiroyuki Sanada",
    			1, "null","null");    	
    	Movie expresult5 = new Movie(20, "kick-ass", "Action, Comedy", "Matthew Vaughn", 
    			"117 min", "Dave Lizewski is an unnoticed high school student and comic book fan with a few friends and who lives alone with his father. His life is not very difficult and his personal trials not that overwhelming. However, one day he makes the simple decision to become a super-hero even though he has no powers or training.",
    			"", "null", "R", "DVD", 2010, "Aaron Taylor-Johnson, Garrett M. Brown, Evan Peters, Deborah Twiss",
    			1, "null", "null");  
    	expresult.add(expresult1);
    	expresult.add(expresult2);
    	expresult.add(expresult3);
    	expresult.add(expresult4);
    	expresult.add(expresult5);
    	
    	List<Movie> result = instance.findMovieByGenre(genre);
    	
    	assertEquals(expresult, result);
    	
    	
    }

    /**
     * Test of updateMovie method, of class MySqlMovieDao.
     */
    @Test
    public void testUpdateMovie() throws Exception {
        System.out.println("updateMovie");
        int id = 23;
        String target = "title";
        String detail = "elektra";
        MovieDaoInterface instance = new MySqlMovieDao();
        Movie result = instance.updateMovie(id, target, detail);
        assertEquals(detail, result.getTitle());
    }
    
    /**
     * Test of findMovieById method, of class MySqlMovieDao.
     */
    @Test
    public void testFindMovieByTitle() throws Exception {
        System.out.println("findMovieByTitle");
        String title = "x men";
        List<Movie> expResult = new ArrayList<>();
        Movie expResult1 = new Movie(16, "x men first class", "Action, Adventure, Sci-Fi", "Matthew Vaughn",
                "132 min", "Before Charles Xavier and Erik Lensherr took the names Professor X and Magneto, they were two young men discovering their powers for the first time. Before they were archenemies, they were closest of friends, working together, with other Mutants (some familiar, some new), to stop the greatest threat the world has ever known. In the process, a rift between them opened, which began the eternal war between Magneto's Brotherhood and Professor X's X-MEN.",
                "", "null", "PG-13", "DVD", 2011, "James McAvoy, Laurence Belcher, Michael Fassbender, Bill Milner",
                1, "null", "null");
        Movie expResult2 = new Movie(24, "x men origins wolverine", "x men origins wolverine", "Gavin Hood",
                "107 min", "Two mutant brothers, Logan and Victor, born 200 years ago, suffer childhood trauma and have only each other to depend on. Basically, they're fighters and killers, living from war to war through U.S. history. In modern times, a U.S. colonel, Stryker, recruits them and other mutants as commandos. Logan quits and becomes a logger, falling in love with a local teacher. When Logan refuses to rejoin Stryker's crew, the colonel sends the murderous Victor. Logan now wants revenge.",
                "", "null", "PG-13", "DVD", 2009, "Hugh Jackman, Liev Schreiber, Danny Huston, Will.i.am",
                1, "null", "null");
        Movie expResult3 = new Movie(27, "x men the last stand", "Action, Adventure, Sci-Fi", "Brett Ratner",
                "104 min", "It has been several months since The X-Men stopped William Stryker, but that victory came at a price: they have lost Jean Grey when she tried to save them from the collapsed reservoir. Scott Summers (Cyclops) is still grieving about her loss. One day, he comes out to the place where Jean Grey sacrificed herself. Jean Grey appears right in front of him. Meanwhile, the rift between humans and mutants has finally reached the boiling point. Humans have discovered what causes humans to mutate and have found a cure for the mutation. The X-Men are appalled at this idea. When news about the cure comes to Magneto, he decides to organize an army of mutants and wage his war against the humans. When Jean Grey evolves into the Phoenix, her new mutant powers are so strong that she can not control her own body. Then, she kills off Professor X with her new powers. Now, The X-Men must stop Magneto again and put an end to the war against the humans, as well as stop Jean Grey's Phoenix powers.",
                "", "null", "PG-13", "DVD", 2006, "Hugh Jackman, Halle Berry, Ian McKellen, Patrick Stewart",
                1, "null", "null");
        expResult.add(expResult1);
        expResult.add(expResult2);
        expResult.add(expResult3);
        MovieDaoInterface instance = new MySqlMovieDao();
        List<Movie> result = instance.findMovieByTitle(title);
        assertEquals(expResult, result);
    }
    
    /**
     * Test findMovieByDirector Method, In the MySqlMovieDao class
     * **/
    
    @Test
    public void testFindMovieByDirector() throws Exception{
    	
    	System.out.println("findMoviebyDirector");
    	String director = "boshi pan";
    	
    	List<Movie> expResult = new ArrayList<>();
    	
    	Movie expResult1 = new Movie(15, "iron man 2", "Disney, marvel,Action, Adventure, Sci-Fi", "boshi pan",
                "124 min", "With the world now aware of his dual life as the armored superhero Iron Man, billionaire inventor Tony Stark faces pressure from the government, the press, and the public to share his technology with the military. Unwilling to let go of his invention, Stark, along with Pepper Potts, and James 'Rhodey' Rhodes at his side, must forge new alliances - and confront powerful enemies.",
                "", "null", "PG-13", "DVD", 2008, "Robert Downey Jr., Gwyneth Paltrow, Don Cheadle, Scarlett Johansson",
                3, "null", "null");
    	
    	expResult.add(expResult1);
    	
    	MovieDaoInterface instance = new MySqlMovieDao();
    	List<Movie> result = instance.findMovieByDirector(director);
    	
    	assertEquals(expResult,result);
    	
    }
    
    /**
     * Test of findMovieByYear, in the MySqlMovieDao class
     * **/
    @Test
    public void testFindMovieByYear() throws Exception{
    	
    	System.out.println("findMovieByYear");
    	int year = 1980;
    	
    	List<Movie> expResult = new ArrayList<>();
    	
    	Movie expResult1 = new Movie(33, "Star Wars: Episode V - The Empire Strikes Back", "Action, Adventure, Fantasy", "Irvin Kershner",
                "124 min", "After the Rebel base on the icy planet Hoth is taken over by the empire, Han, Leia, Chewbacca, and C-3PO flee across the galaxy from the Empire. Luke travels to the forgotten planet of Dagobah to receive training from the Jedi master Yoda, while Vader endlessly pursues him.",
                "", "null", "PG", "DVD", 1980, "Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams",
                1, "5039036028295", "null");
    	
    	
    	Movie expResult2 = new Movie(63, "Raging Bull","Biography, Drama, Sport","Martin Scorsese",
    		    "129 min","When Jake LaMotta steps into a boxing ring and obliterates his opponent, he's a prizefighter. But when he treats his family and friends the same way, he's a ticking time bomb, ready to go off at any moment. Though LaMotta wants his family's love, something always seems to come between them. Perhaps it's his violent bouts of paranoia and jealousy. This kind of rage helped make him a champ, but in real life, he winds up in the ring alone.",
    			"","null","R","DVD", 1980,"Robert De Niro, Cathy Moriarty, Joe Pesci, Frank Vincent",
    			1,"","null");
    	
    	Movie expResult3 = new Movie(96,"The Blues Brothers","Action, Comedy, Crime","John Landis",
    			"133 min","After the release of Jake Blues from prison, he and brother Elwood go to visit 'The Penguin', the last of the nuns who raised them in a boarding school. They learn the Archdiocese will stop supporting the school and will sell the place to the Education Authority. The only way to keep the place open is if the $5000 tax on the property is paid within 11 days. The Blues Brothers want to help, and decide to put their blues band back together and raise the the money by staging a big gig. As they set off on their 'mission from God' they seem to make more enemies along the way. Will they manage to come up with the money in time?",
    			"","null","R","DVD",1980,"John Belushi, Dan Aykroyd, James Brown, Cab Calloway",
    			1,"5050582359473","null");
    	
    	Movie expResult4 = new Movie(171,"Caddyshack","Comedy, Sport","Harold Ramis",
    			"98 min","Comical goings on at an exclusive golf club. All the members are wealthy and eccentric, and all the staff are poor and slightly less eccentric. The main character is 'Danny'; he's a caddy who will do almost anything to raise money to go to college. There are many subplots, including the assistant green keeper's pursuit of a cute (obviously stuffed) gopher.",
    			"","null","R","DVD",1980,"Chevy Chase, Rodney Dangerfield, Ted Knight, Michael O'Keefe",
    			1,"7321900020053","null");
    	
    	Movie expResult5 = new Movie(256,"The Long Good Friday","Action, Crime, Drama","John Mackenzie",
    			"114 min","Harold, a prosperous English gangster, is about to close a lucrative new deal when bombs start showing up in very inconvenient places. A mysterious syndicate is trying to muscle in on his action, and Harold wants to know who they are. He finds out soon enough, and bloody mayhem ensues.",
    			"","null","R","DVD",1980,"Bob Hoskins, Helen Mirren, Dave King, Bryan Marshall",
    			1,"5060020700699","7.8");
    	
    	Movie  expResult6 = new Movie(427,"The Shining","Horror","Stanley Kubrick",
    			"146 min","Signing a contract, Jack Torrance, a normal writer and former teacher agrees to take care of a hotel which has a long, violent past that puts everyone in the hotel in a nervous situation. While Jack slowly gets more violent and angry of his life, his son, Danny, tries to use a special talent, the 'Shining', to inform the people outside about whatever that is going on in the hotel.",
    			"","null","R","DVD",1980,"Jack Nicholson, Shelley Duvall, Danny Lloyd, Scatman Crothers",
    			1,"7321900211567","8.5");
    	
    	Movie expResult7 = new Movie(458,"Airplane!","Comedy","Jim Abrahams, David Zucker, Jerry Zucker",
    			"88 min","Still craving for the love of his life, Ted Striker follows Elaine onto the flight that she is working on as a member of the cabin crew. Elaine doesn't want to be with Ted anymore, but when the crew and passengers fall ill from food poisoning, all eyes are on Ted.",
    			"","null","PG","DVD",1980,"Kareem Abdul-Jabbar, Lloyd Bridges, Peter Graves, Julie Hagerty",
    			1,"5014437912435","7.8");
    	
    	Movie expResult8 =  new Movie(514,"Private Benjamin","Comedy, War","Howard Zieff",
    			"109 min","When her husband dies on their wedding night, Judy decides to join the United States Army. She realizes that she has never been independent in her entire life. What looks like a bad decision at first, turns out not so bad at all. That is, until her superior officer makes sexual advances on her. She has been transferred to NATO headquarters in Europe and (re)meets the Frenchman Henri Tremont. Judy and Henri decide to marry, but will they?",
    			"","null","R","DVD",1980,"Goldie Hawn, Eileen Brennan, Armand Assante, Robert Webber",
    			1,"7321900110754","6.1");
    	
    	expResult.add(expResult1);
    	expResult.add(expResult2);
    	expResult.add(expResult3);
    	expResult.add(expResult4);
    	expResult.add(expResult5);
    	expResult.add(expResult6);
    	expResult.add(expResult7);
    	expResult.add(expResult8);
    	
    	MovieDaoInterface instance = new MySqlMovieDao();
    	List<Movie> result = instance.findMovieByYear(year);
    	
    	assertEquals(result,expResult);
    }
}
