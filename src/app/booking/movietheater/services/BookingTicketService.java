package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import app.booking.movietheater.entities.GlobalHelper;
import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.entities.Price;
import app.booking.movietheater.entities.Room;
import app.booking.movietheater.entities.Session;
import app.booking.movietheater.entities.BookingPlace;
import app.booking.movietheater.entities.BookingTicket;
import app.booking.movietheater.exceptions.BookingPlaceAvailablePlaceException;
import app.booking.movietheater.exceptions.BookingTicketNotFoundException;
import app.booking.movietheater.exceptions.BookingTicketThreadException;
import app.booking.movietheater.exceptions.BookingTicketValidationException;
import app.booking.movietheater.threads.BookingPlacesRunnable;
import app.booking.movietheater.threads.BookingTicketUniqueIDRunnable;

public class BookingTicketService {
	private static BookingTicketService instance = null;
	private ArrayList<BookingTicket> bookingTickets;

	private BookingTicketService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.bookingTickets = new ArrayList<>();
		}
	}

	public synchronized String generateUniqueID() {
		String uniqueID = UUID.randomUUID().toString();
		Boolean founded = true;
		while (founded) {
			founded = false;
			uniqueID = UUID.randomUUID().toString();
			for (int i = 0; i < this.bookingTickets.size(); i++)
				if (this.bookingTickets.get(i).BookingID.equals(uniqueID)) {
					founded = true;
					break;
				}
		}
		return uniqueID;
	}

	public static BookingTicketService getInstance() {
		if (instance == null)
			instance = new BookingTicketService();

		return instance;
	}

	public ArrayList<BookingTicket> listBookingTickets() {
		return bookingTickets;
	}

	public void createBookingTicket(Session session, ArrayList<Price> prices)
			throws BookingTicketValidationException, BookingTicketThreadException {
		if (session == null)
			throw new BookingTicketValidationException("Session required!!");

		if (prices == null || prices.size() <= 0)
			throw new BookingTicketValidationException("Places required!!");

		BookingTicket bookingTicket = new BookingTicket();
		bookingTicket.Session = session;
		Thread threadUniqueID = new Thread(new BookingTicketUniqueIDRunnable(bookingTicket));

		Thread threadBookingPlaces = new Thread(new BookingPlacesRunnable(bookingTicket, prices));

		threadUniqueID.start();

		threadBookingPlaces.start();

		try {
			threadUniqueID.join();
		} catch (InterruptedException e) {
			throw new BookingTicketThreadException("Please try again");
		}

		try {
			threadBookingPlaces.join();
		} catch (InterruptedException e) {
			throw new BookingTicketThreadException("Please try again");
		}

		bookingTickets.add(bookingTicket);
	}

	public BookingTicket getBookingTicket(String BookingID) {
		for (int i = 0; i < bookingTickets.size(); i++) {
			if (bookingTickets.get(i).BookingID.equals(BookingID))
				return bookingTickets.get(i);
		}
		return null;
	}

	public ArrayList<BookingTicket> getBookingTickets(Session session) {
		ArrayList<BookingTicket> bookingTickets = new ArrayList<>();
		if (session != null)
			for (int i = 0; i < bookingTickets.size(); i++)
				if (bookingTickets.get(i).Session.SessionID == session.SessionID)
					bookingTickets.add(bookingTickets.get(i));

		return bookingTickets;
	}

	public int getIndexBookingTicket(String BookingID) {
		for (int i = 0; i < bookingTickets.size(); i++)
			if (bookingTickets.get(i).BookingID.equals(BookingID))
				return i;
		return -1;
	}

	public void deleteBookingTicket(String BookingID) throws BookingTicketNotFoundException {
		int indexBookingTicket = this.getIndexBookingTicket(BookingID);
		if (indexBookingTicket > -1)
			bookingTickets.remove(indexBookingTicket);
		else
			throw new BookingTicketNotFoundException();

	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("bookingTickets.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.bookingTickets);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("bookingTickets.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.bookingTickets = (ArrayList<BookingTicket>) obj;
		stream.close();
		objectStream.close();

	}
}
