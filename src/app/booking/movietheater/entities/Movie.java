package app.booking.movietheater.entities;

import java.io.Serializable;
import java.util.Date;

public class Movie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6656212108752296278L;
	public int MovieID;
	public String Name;
	public Date MovieDate;
	public int Duration;
	
	

	@Override
	public String toString() {
		return "==============\n" + "MovieID: " + MovieID + "\nName: " + Name + "\n==============\n\n";
	}
}
