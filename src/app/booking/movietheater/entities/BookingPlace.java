package app.booking.movietheater.entities;

import java.io.Serializable;

public class BookingPlace implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1528735190402093116L;
	public Price Price;
	public BookingTicket BookingTicket;
	public int placeNumber;
	// the booking ticket price repeated by the number of quantity
	// for example booking ticket 4 places
	// so the booking ticket price will create 4 times to specify number of place
	// for each one

	public BookingPlace(BookingTicket bookingTicket, Price price, int placeNumber) {
		this.Price = price;
		this.BookingTicket = bookingTicket;
		this.placeNumber = placeNumber;
	}

	@Override
	public String toString() {
		return "==============\n" + "PriceName: " + Price.Name + "\nPrice: " + Price.Price + "\nPlaceNumber: "
				+ placeNumber + "\n==============\n\n";
	}

}
