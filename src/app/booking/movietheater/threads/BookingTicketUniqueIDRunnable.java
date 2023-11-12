package app.booking.movietheater.threads;

import app.booking.movietheater.entities.BookingTicket;
import app.booking.movietheater.services.BookingTicketService;

public class BookingTicketUniqueIDRunnable implements Runnable {
	BookingTicket bookingTicket;
	BookingTicketService bookingTicketService = BookingTicketService.getInstance();
	public BookingTicketUniqueIDRunnable(BookingTicket bookingTicket) {
		this.bookingTicket = bookingTicket;
	}
	@Override
	public void run() {
		this.bookingTicket.BookingID = this.bookingTicketService.generateUniqueID();
	}

}
