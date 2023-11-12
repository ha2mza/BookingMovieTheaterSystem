package app.booking.movietheater.entities;

import java.io.Serializable;
import java.util.Date;

public class BookingTicket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6961679266814377802L;
	public String BookingID;
	public Date BookingDate;
	public int QuantityTotal;
	public Session Session;
	public BookingState bookingState = BookingState.PENDING;
	public String Note = "";
}
