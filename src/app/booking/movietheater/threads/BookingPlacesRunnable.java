package app.booking.movietheater.threads;

import java.util.ArrayList;

import app.booking.movietheater.entities.BookingPlace;
import app.booking.movietheater.entities.BookingState;
import app.booking.movietheater.entities.BookingTicket;
import app.booking.movietheater.entities.Price;
import app.booking.movietheater.exceptions.BookingPlaceAvailablePlaceException;
import app.booking.movietheater.services.BookingPlaceService;
import app.booking.movietheater.services.SessionService;

public class BookingPlacesRunnable implements Runnable {

	ArrayList<Price> prices;
	BookingTicket bookingTicket;
	BookingPlaceService bookingPlaceService = BookingPlaceService.getInstance();
	public BookingPlacesRunnable(BookingTicket bookingTicket, ArrayList<Price> prices) {
		this.prices = prices;
		this.bookingTicket = bookingTicket;
	}
	
	@Override
	public void run() {
		
		try {
			this.bookingPlaceService.setPlaces(this.bookingTicket, this.prices);
		} catch (BookingPlaceAvailablePlaceException e) {
			this.bookingTicket.bookingState = BookingState.CANCELED;
			this.bookingTicket.Note = e.getMessage();
		}
		
	}

}
