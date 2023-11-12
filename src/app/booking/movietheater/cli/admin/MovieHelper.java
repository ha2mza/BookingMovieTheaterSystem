package app.booking.movietheater.cli.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.exceptions.MovieValidationException;
import app.booking.movietheater.services.MovieService;

public class MovieHelper {
	private static MovieService movieService = MovieService.getInstance();

	public static void listMovies() {
		ArrayList<Movie> movies = movieService.listMovies();

		for (int i = 0; i < movies.size(); i++) {
			System.out.println(movies.get(i));
		}
	}

	public static void addMovie() {
		Boolean _continue = true;
		Program console = Program.console();

		while (_continue) {
			System.out.println("Form Add Movie: \n\n");
			String name = console.readLine("Put Name: ");
			String date = console.readLine("Put MovieDate(yyyy-MM-dd): ");
			String duration = console.readLine("Put MovieDuration: ");
			try {
				movieService.createMovie(name, date, duration);
				_continue = false;
			} catch (MovieValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}
}
