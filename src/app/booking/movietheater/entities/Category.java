package app.booking.movietheater.entities;

import java.io.Serializable;

public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4302558771040404277L;
	public String CategoryID;
	public String Name;
	

	@Override
	public String toString() {
		return "==============\n" + "CategoryID: " + CategoryID + "\nName: " + Name + "\n==============\n\n";
	}
}
