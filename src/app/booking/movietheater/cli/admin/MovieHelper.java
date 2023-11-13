package app.booking.movietheater.cli.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.booking.movietheater.entities.Category;
import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.entities.Price;
import app.booking.movietheater.exceptions.CategoryMovieValidationException;
import app.booking.movietheater.exceptions.CategoryNotFoundException;
import app.booking.movietheater.exceptions.CategoryValidationException;
import app.booking.movietheater.exceptions.MovieNotFoundException;
import app.booking.movietheater.exceptions.MovieValidationException;
import app.booking.movietheater.exceptions.PriceValidationException;
import app.booking.movietheater.services.CategoryMovieService;
import app.booking.movietheater.services.CategoryService;
import app.booking.movietheater.services.MovieService;
import app.booking.movietheater.services.PriceService;

public class MovieHelper {
	private static MovieService movieService = MovieService.getInstance();
	private static CategoryService categoryService = CategoryService.getInstance();
	private static CategoryMovieService categoryMovieService = CategoryMovieService.getInstance();
	private static PriceService priceService = PriceService.getInstance();

	public static void listMovies() {
		ArrayList<Movie> movies = movieService.listMovies();

		for (int i = 0; i < movies.size(); i++) {
			System.out.println(movies.get(i));
			ArrayList<Price> prices = priceService.getPrices(movies.get(i));
			ArrayList<Category> categories = categoryMovieService.getCategories(movies.get(i).MovieID);

			System.out.println("=> Prices:");
			for (int j = 0; j < prices.size(); j++) {
				System.out.println(prices.get(j));
			}

			System.out.println("=> Categories:");
			for (int j = 0; j < categories.size(); j++) {
				System.out.println(categories.get(j));
			}
		}
	}

	public static void addMovie() {
		Boolean _continue = true;
		Program console = Program.console();

		while (_continue) {
			System.out.println("Form Add Movie: \n\n");
			System.out.println("==> Movie Details \n\n");
			String name = console.readLine("Put Name: ");
			String date = console.readLine("Put MovieDate(yyyy-MM-dd): ");
			String duration = console.readLine("Put MovieDuration: ");

			Movie movie;
			try {
				movie = movieService.createMovie(name, date, duration);
				_continue = false;
			} catch (MovieValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());

				continue;
			}

			System.out.println("> Movie has been created \n\n");

			ArrayList<Category> categories = new ArrayList<>();
			System.out.println("==> Categories \n\n");
			String choice = console.readLine("Attach Category [Y/N]: ");
			while (choice.toLowerCase().equals("y")) {
				Category category;
				try {
					String categoryID = console.readLine("Put categoryID: ");
					category = categoryService.getCategoryOrException(categoryID);
				} catch (CategoryNotFoundException e) {
					System.out.println(e.getMessage());
					continue;
				}

				categories.add(category);
				choice = console.readLine("Attach Other Category [Y/N]: ");
			}

			categoryMovieService.setCategories(movie, categories);

			System.out.println("==> Prices \n\n");
			choice = "y";
			while (choice.toLowerCase().equals("y")) {
				try {
					String price = console.readLine("Put price: ");
					name = console.readLine("Put name: ");
					priceService.createPrice(name, price, movie);
				} catch (PriceValidationException e) {
					System.out.println(e.getMessage());
					continue;
				}
				choice = console.readLine("Attach Other Price [Y/N]: ");
			}

		}
	}

	public static void editMovie() {
		Boolean _continue = true;
		Program console = Program.console();

		while (_continue) {
			System.out.println("Form Edit Movie: \n\n");
			String id = console.readLine("Put MovieID: ");
			String name = console.readLine("Put Name: ");
			String date = console.readLine("Put MovieDate(yyyy-MM-dd): ");
			String duration = console.readLine("Put MovieDuration: ");

			try {
				movieService.editMovie(id, name, date, duration);
				_continue = false;
			} catch (MovieValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (MovieNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

		}

	}

	public static void removeMovie() {
		Boolean _continue = true;
		Program console = Program.console();

		while (_continue) {
			System.out.println("Form Delete Movie: \n\n");
			String id = console.readLine("Put MovieID: ");

			try {
				movieService.deleteMovie(id);
				_continue = false;
			}  catch (MovieNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}  catch (MovieValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}

		}
		
	}
}
