package app.booking.movietheater.entities;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7922911826168268447L;
	public int SessionID;
	public SessionState sessionState = SessionState.PENDING;
	public String Name;
	public Date Time;
	public Movie Movie;
	public MovieTheater MovieTheater;
	public Room Room;
}