package app.booking.movietheater.exceptions;

public class SessionNotFoundException extends Exception {
	public SessionNotFoundException() {
		super("Session not found");
	}
}
