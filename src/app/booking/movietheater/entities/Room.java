package app.booking.movietheater.entities;

import java.io.Serializable;

public class Room implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -750181978751251648L;
	public int RoomID;
	public String Name;
	public int Capacity;
	public MovieTheater MovieTheater;
	
	
	@Override
	public String toString() {
		return "==============\n" + 
	"RoomID: " + RoomID + 
	"\nName: " + Name + 
	"\nCapacity: " + Capacity + 
	"\n==============\n\n";
	}
	
}
