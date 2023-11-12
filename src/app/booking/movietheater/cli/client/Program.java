package app.booking.movietheater.cli.client;

import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.exceptions.MovieTheaterValidationException;
import app.booking.movietheater.exceptions.RoomValidationException;
import app.booking.movietheater.exceptions.SessionValidationException;
import app.booking.movietheater.services.MovieService;
import app.booking.movietheater.services.MovieTheaterService;
import app.booking.movietheater.services.RoomService;
import app.booking.movietheater.services.SessionService;

public class Program {

	public static void main(String[] args) {
		try {
			MovieTheaterService.getInstance().createMovieTheater("A1", "Casablanca");
			MovieTheaterService.getInstance().createMovieTheater("A2", "Casablanca");
		} catch (MovieTheaterValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			RoomService.getInstance().createRoom("S1", 50, MovieTheaterService.getInstance().getMovieTheater(1));
			RoomService.getInstance().createRoom("S2", 25, MovieTheaterService.getInstance().getMovieTheater(1));
		} catch (RoomValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SessionService.getInstance().createSession("4D 14h00", 
					"2023-11-12",
					MovieService.getInstance().getMovie(1), 
					MovieTheaterService.getInstance().getMovieTheater(1), 
					RoomService.getInstance().getRoom(1));
		} catch (SessionValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SessionService.getInstance().createSession("3D 20h00", 
					"2023-11-12",
					MovieService.getInstance().getMovie(1), 
					MovieTheaterService.getInstance().getMovieTheater(1), 
					RoomService.getInstance().getRoom(1));
		} catch (SessionValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
