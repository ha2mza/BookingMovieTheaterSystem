package app.booking.movietheater.exceptions;

public class RoomNotFoundException extends Exception {
	public RoomNotFoundException() {
		super("Room not found");
	}
}
