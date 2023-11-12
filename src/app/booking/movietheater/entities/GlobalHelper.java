package app.booking.movietheater.entities;

public class GlobalHelper {
	public static Sequance sequance = Sequance.getInstance();
	

	public static int getNextMovieID() {
		return ++sequance.MovieID;
	}
	

	public static int getNextMovieTheaterID() {
		return ++sequance.MovieTheaterID;
	}
	

	public static int getNextRoomID() {
		return ++sequance.RoomID;
	}
	

	public static int getNextPriceID() {
		return ++sequance.PriceID;
	}

	public static int getNextSessionID() {
		return ++sequance.SessionID;
	}
}
