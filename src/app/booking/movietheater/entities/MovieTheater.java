package app.booking.movietheater.entities;

import java.io.Serializable;

public class MovieTheater implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7315024732092758403L;
	public int MovieTheaterID;
	public String Name;
	public String Address;
	
	
	@Override
	public String toString() {
		return "==============\n" + 
	"MovieTheaterID: " + MovieTheaterID + 
	"\nName: " + Name + 
	"\nAddress: " + Address + 
	"\n==============\n\n";
	}
	
}
