package app.booking.movietheater.exceptions;

public class MovieNotFoundException extends Exception {
	public MovieNotFoundException() {
		super("Movie not found");
	}
}
