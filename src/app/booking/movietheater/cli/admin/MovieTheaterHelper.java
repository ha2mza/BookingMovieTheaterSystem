package app.booking.movietheater.cli.admin;

import java.util.ArrayList;

import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.entities.Room;
import app.booking.movietheater.exceptions.CategoryValidationException;
import app.booking.movietheater.exceptions.MovieTheaterNotFoundException;
import app.booking.movietheater.exceptions.MovieTheaterValidationException;
import app.booking.movietheater.exceptions.PriceValidationException;
import app.booking.movietheater.exceptions.RoomValidationException;
import app.booking.movietheater.services.MovieTheaterService;
import app.booking.movietheater.services.RoomService;

public class MovieTheaterHelper {
	private static MovieTheaterService movieTheaterService = MovieTheaterService.getInstance();
	private static RoomService  roomService = RoomService.getInstance();
	
	
	public static void listMovieTheaters() {

		ArrayList<MovieTheater> movieTheaters = movieTheaterService.listMovieTheaters();

		for (int i = 0; i < movieTheaters.size(); i++) {
			System.out.println(movieTheaters.get(i));
			ArrayList<Room> rooms = roomService.getRooms(movieTheaters.get(i));

			System.out.println("=> Rooms:");
			for (int j = 0; j < rooms.size(); j++) {
				System.out.println(rooms.get(j));
			}
		}
	}
	
	public static void createMovieTheater() {
		Boolean _continue = true;
		Program console = Program.console();
		MovieTheater movieTheater = null;
		while (_continue) {
			System.out.println("Form Add MovieTheater: \n\n");
			String name = console.readLine("Put Name: ");
			String address = console.readLine("Put Address: ");
			try {
				movieTheater = movieTheaterService.createMovieTheater(name, address);
				_continue = false;
				System.out.println("> MovieTheater has been created \n\n");
			} catch (MovieTheaterValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		


		String choice;
		System.out.println("==> Rooms \n\n");
		choice = "y";
		while (choice.toLowerCase().equals("y")) {
			try {
				String name = console.readLine("Put RoomName: ");
				String capacity = console.readLine("Put Capacity: ");
				roomService.createRoom(name, capacity, movieTheater);
			} catch (RoomValidationException e) {
				System.out.println(e.getMessage());
				continue;
			}
			choice = console.readLine("Put Other Room [Y/N]: ");
		}
		
		
		
	}
	
	public static void editMovieTheater() {
		Boolean _continue = true;
		Program console = Program.console();
		while (_continue) {
			System.out.println("Form Edit MovieTheater: \n\n");
			String id = console.readLine("Put MovieTheaterID: ");
			String name = console.readLine("Put Name: ");
			String address = console.readLine("Put Address: ");
			try {
				movieTheaterService.editMovieTheater(id, name, address);
				_continue = false;
			} catch (MovieTheaterValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}catch (MovieTheaterNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	public static void removeMovieTheater() {

		Boolean _continue = true;
		Program console = Program.console();
		while (_continue) {
			System.out.println("Form Delete MovieTheater: \n\n");
			String id = console.readLine("Put MovieTheaterID: ");
			try {
				movieTheaterService.deleteMovieTheater(id);
				_continue = false;
			} catch (MovieTheaterValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}catch (MovieTheaterNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
		
	}
}
