package app.booking.movietheater.exceptions;

public class MovieTheaterNotFoundException extends Exception {
	public MovieTheaterNotFoundException() {
		super("MovieTheater not found");
	}
}
