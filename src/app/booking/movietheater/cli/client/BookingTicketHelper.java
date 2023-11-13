package app.booking.movietheater.cli.client;

import java.util.ArrayList;

import app.booking.movietheater.cli.admin.Program;
import app.booking.movietheater.entities.BookingPlace;
import app.booking.movietheater.entities.BookingState;
import app.booking.movietheater.entities.BookingTicket;
import app.booking.movietheater.entities.Category;
import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.entities.Price;
import app.booking.movietheater.entities.Session;
import app.booking.movietheater.exceptions.BookingPlaceAvailablePlaceException;
import app.booking.movietheater.exceptions.BookingTicketThreadException;
import app.booking.movietheater.exceptions.BookingTicketValidationException;
import app.booking.movietheater.exceptions.CategoryNotFoundException;
import app.booking.movietheater.exceptions.CategoryValidationException;
import app.booking.movietheater.exceptions.SessionValidationException;
import app.booking.movietheater.services.BookingPlaceService;
import app.booking.movietheater.services.BookingTicketService;
import app.booking.movietheater.services.MovieService;
import app.booking.movietheater.services.MovieTheaterService;
import app.booking.movietheater.services.PriceService;
import app.booking.movietheater.services.RoomService;
import app.booking.movietheater.services.SessionService;

public class BookingTicketHelper {
	private static BookingTicketService bookingTicketService = BookingTicketService.getInstance();
	private static SessionService sessionService = SessionService.getInstance();
	private static MovieService movieService = MovieService.getInstance();
	private static MovieTheaterService movieTheaterService = MovieTheaterService.getInstance();
	private static PriceService priceService = PriceService.getInstance();
	private static BookingPlaceService bookingPlaceService = BookingPlaceService.getInstance();

	public static void newBookingTicket() {
		Boolean _continue = true;
		Program console = Program.console();

		ArrayList<Movie> movies = null;
		ArrayList<Session> sessions = null;
		ArrayList<MovieTheater> movieTheaters = movieTheaterService.listMovieTheaters();

		while (_continue) {
			System.out.println("Form New BookingTicket: \n\n");

			System.out.println("MovieTheaters>> \n");

			MovieTheater movieTheater = null;
			while (movieTheater == null) {
				for (int i = 0; i < movieTheaters.size(); i++) {
					System.out.println("(" + movieTheaters.get(i).MovieTheaterID + "). " + movieTheaters.get(i).Name
							+ " // " + movieTheaters.get(i).Address + "\n");
				}
				System.out.println("==============");
				String movieTheaterID = console.readLine("Put MovieTheater Option: ");
				movieTheater = movieTheaterService.getMovieTheater(movieTheaterID);
			}

			System.out.println("Movies>> \n");
			movies = sessionService.getMovies(movieTheater.MovieTheaterID);
			Movie movie = null;
			while (movie == null) {
				for (int i = 0; i < movies.size(); i++) {
					System.out.println("(" + movies.get(i).MovieID + "). " + movies.get(i).Name + " // "
							+ movies.get(i).Duration + "min\n");
				}
				System.out.println("==============");
				String movieID = console.readLine("Put Movie Option: ");
				movie = movieService.getMovie(movieID);
			}

			System.out.println("Sessions>> \n");

			sessions = sessionService.getSessions(movie, movieTheater);

			Session session = null;

			while (session == null) {
				for (int i = 0; i < sessions.size(); i++) {
					System.out.println("(" + sessions.get(i).SessionID + "). " + sessions.get(i).Name + " // "
							+ sessions.get(i).Room + " // " + sessions.get(i).Time + "\n");
				}
				System.out.println("==============");
				String sessionID = console.readLine("Put Session Option: ");
				session = sessionService.getSession(sessionID);
			}

			System.out.println("BookingDetails>>");
			int QuantityTotal = -1;
			while (QuantityTotal <= 0) {
				String _QuantityTotal = console.readLine("Number of person?: ");
				try {
					QuantityTotal = Integer.parseInt(_QuantityTotal);
				} catch (NumberFormatException e) {

				}
			}

			ArrayList<Price> prices = priceService.getPrices(movie);

			System.out.println("** Put Category Price per each person");

			for (int i = 0; i < prices.size(); i++) {
				System.out.println("- Category (" + prices.get(i).PriceID + "): " + prices.get(i).Name + " // "
						+ prices.get(i).Price + "\n");
			}
			ArrayList<Price> selected_prices = new ArrayList<>();

			for (int i = 1; i <= QuantityTotal; i++) {
				Price price = null;
				System.out.println("==============");
				while (price == null) {
					String priceID = console.readLine("Category price of person " + i + ": ");
					price = priceService.getPrice(priceID);
				}
				selected_prices.add(price);
			}

			try {
				BookingTicket bookingTicket = bookingTicketService.createBookingTicket(session, selected_prices);

				if (bookingTicket.bookingState == BookingState.DONE) {
					System.out.println("The Booking Ticket has been created success");
					System.out.println(bookingTicket);
					_continue = false;
				} else {
					System.out.println(bookingTicket.Note);
				}
			} catch (BookingTicketValidationException e) {
				System.out.println(e);
			} catch (BookingTicketThreadException e) {
				System.out.println(e);
			}

		}
	}

	public static void displayBookingTicket() {
		Boolean _continue = true;
		Program console = Program.console();

		System.out.println("Form Display BookingTicket: \n\n");
		String bookingID = console.readLine("Put BookingID: ");
		BookingTicket bookingTicket = bookingTicketService.getBookingTicket(bookingID);

		if (bookingTicket != null) {
			System.out.println(bookingTicket);
			System.out.println("======================");
			ArrayList<BookingPlace> places = bookingPlaceService.getPlaces(bookingID);
			
			places.forEach((p) -> System.out.println(p));
		} else {
			System.out.println("BookingTicket not found");
		}

	}

}
