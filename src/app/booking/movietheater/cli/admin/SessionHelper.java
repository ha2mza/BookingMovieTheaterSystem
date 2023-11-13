package app.booking.movietheater.cli.admin;

import java.util.ArrayList;

import app.booking.movietheater.entities.Category;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.entities.Session;
import app.booking.movietheater.exceptions.CategoryValidationException;
import app.booking.movietheater.exceptions.SessionValidationException;
import app.booking.movietheater.services.MovieService;
import app.booking.movietheater.services.MovieTheaterService;
import app.booking.movietheater.services.RoomService;
import app.booking.movietheater.services.SessionService;

public class SessionHelper {
	private static SessionService sessionService = SessionService.getInstance();
	private static MovieService movieService = MovieService.getInstance();
	private static MovieTheaterService movieTheaterService = MovieTheaterService.getInstance();
	private static RoomService roomService = RoomService.getInstance();
	
	
	public static void listSessions() {
		ArrayList<Session> sessions = sessionService.listSessions();

		for (int i = 0; i < sessions.size(); i++) {
			System.out.println(sessions.get(i));
		}
	}
	
	
	public static void createSession() {
		Boolean _continue = true;
		Program console = Program.console();

		while (_continue) {
			System.out.println("Form Add Session: \n\n");
			String name = console.readLine("Put Name: ");
			String movieID = console.readLine("Put MovieID: ");
			String movieTheaterID = console.readLine("Put MovieTheaterID: ");
			String roomID = console.readLine("Put RoomID: ");
			String time = console.readLine("Put Time (yyy-mm-dd): ");
			try {
				MovieTheater movieTheater =  movieTheaterService.getMovieTheater(movieTheaterID);
				sessionService.createSession(name, time, movieService.getMovie(movieID), movieTheater, roomService.getRoom(movieTheater, roomID));
				_continue = false;
			} catch (SessionValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	public static void editSession() {
		
		
	}
	
	public static void removeSession() {
		
	}
}

