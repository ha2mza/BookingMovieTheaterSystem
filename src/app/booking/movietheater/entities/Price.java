package app.booking.movietheater.entities;

import java.io.Serializable;

public class Price implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3622089425777895405L;
	public int PriceID;
	public String Name;
	public float Price;
	public Movie Movie;
	

	@Override
	public String toString() {
		return "==============\n" + 
	"PriceID: " + PriceID + 
	"\nName: " + Name + 
	"\nPrice: " + Price + 
	"\n==============\n\n";
	}
	
}
