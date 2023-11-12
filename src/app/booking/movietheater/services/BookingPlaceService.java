package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.booking.movietheater.entities.BookingTicket;
import app.booking.movietheater.entities.Price;
import app.booking.movietheater.entities.Session;
import app.booking.movietheater.exceptions.BookingPlaceAvailablePlaceException;
import app.booking.movietheater.entities.BookingPlace;
import app.booking.movietheater.entities.BookingState;

public class BookingPlaceService {
	private static BookingPlaceService instance = null;
	private ArrayList<BookingPlace> bookingPlaces;

	private BookingPlaceService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.bookingPlaces = new ArrayList<>();
		}
	}

	public static BookingPlaceService getInstance() {
		if (instance == null)
			instance = new BookingPlaceService();

		return instance;
	}

	public ArrayList<BookingPlace> getPlaces(String bookingTicketID) {
		ArrayList<BookingPlace> places = new ArrayList<BookingPlace>();

		for (int i = 0; i < bookingPlaces.size(); i++) {
			if (bookingPlaces.get(i).BookingTicket.BookingID.equals(bookingTicketID))
				places.add(this.bookingPlaces.get(i));
		}
		return places;
	}

	public synchronized void setPlaces(BookingTicket bookingTicket, ArrayList<Price> prices)
			throws BookingPlaceAvailablePlaceException {
		int placeIndex = this.findAvailablePlaces(bookingTicket.Session, prices.size());
		if (placeIndex > -1) {
			for (int i = 0; i < prices.size(); i++) {
				this.bookingPlaces.add(new BookingPlace(bookingTicket, prices.get(i), placeIndex + i));
			}
			bookingTicket.bookingState = BookingState.DONE;
		} else {
			throw new BookingPlaceAvailablePlaceException("Sorry cannot find adjucent places for " + prices.size()
					+ " places, can you try less places please!!");
		}
	}

	public Boolean hasPlace(int sessionID, int placeID) {
		for (int i = 0; i < bookingPlaces.size(); i++) {
			if (bookingPlaces.get(i).placeNumber == placeID
					&& bookingPlaces.get(i).BookingTicket.Session.SessionID == sessionID)
				return true;
		}
		return false;
	}

	public Boolean hasPlaces(int sessionID, int placeID, int numberPlaces, int nextPlace) {
		Boolean hasPlaces = true;
		int[] nextPlaces = new int[numberPlaces];
		int flag = 0;
		for (int i = placeID; i < placeID + numberPlaces; i++) {
			Boolean hasPlace = this.hasPlace(sessionID, placeID);
			hasPlaces = hasPlaces && hasPlace;
			if (hasPlace)
				nextPlaces[(flag++)] = i;
			else {
				flag = 0;
			}
		}
		nextPlace = nextPlaces[0];

		return hasPlaces;
	}

	public int findAvailablePlaces(Session session, int numberPlaces) {
		for (int i = 1; i <= session.Room.Capacity - numberPlaces + 1; i++) {
			int nextPlace = session.Room.Capacity + 1;
			Boolean hasPlaces = this.hasPlaces(session.SessionID, i, numberPlaces, nextPlace);
			if (hasPlaces)
				return i;
			else
				i = nextPlace;
		}

		return -1;
	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("bookingPlaces.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.bookingPlaces);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("bookingPlaces.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.bookingPlaces = (ArrayList<BookingPlace>) obj;
		stream.close();
		objectStream.close();

	}
}
