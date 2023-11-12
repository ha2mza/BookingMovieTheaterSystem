package app.booking.movietheater.exceptions;

public class BookingTicketNotFoundException extends Exception {
	public BookingTicketNotFoundException() {
		super("BookingTicket not found");
	}
}
