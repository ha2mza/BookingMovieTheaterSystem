package app.booking.movietheater.exceptions;

public class PriceNotFoundException extends Exception {
	public PriceNotFoundException() {
		super("Price not found");
	}
}
