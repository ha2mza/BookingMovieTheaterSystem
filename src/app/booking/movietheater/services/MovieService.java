package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.booking.movietheater.entities.Category;
import app.booking.movietheater.entities.CategoryMovie;
import app.booking.movietheater.entities.GlobalHelper;
import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.exceptions.MovieNotFoundException;
import app.booking.movietheater.exceptions.MovieValidationException;

public class MovieService {
	private static MovieService instance = null;
	private ArrayList<Movie> movies;

	private MovieService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.movies = new ArrayList<>();
		}
	}

	public static MovieService getInstance() {
		if (instance == null)
			instance = new MovieService();

		return instance;
	}

	public Movie createMovie(String name, String movieDateString, String _duration) throws MovieValidationException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int duration = 0;

		if (name.length() <= 0)
			throw new MovieValidationException("Name required!!");

		if (movieDateString == null)
			throw new MovieValidationException("MovieDate required!!");
		try {
			duration = Integer.parseInt(_duration);
		} catch (NumberFormatException e) {
			throw new MovieValidationException("Duration not valid!!");
		}

		if (duration <= 0)
			throw new MovieValidationException("Duration required!!");

		Date movieDate = null;

		try {
			movieDate = format.parse(movieDateString);
		} catch (ParseException e) {
			throw new MovieValidationException("MovieDate not valid!!");
		}

		Movie movie = new Movie();
		movie.MovieID = GlobalHelper.getNextMovieID();
		movie.Duration = duration;
		movie.MovieDate = movieDate;
		movie.Name = name;
		this.movies.add(movie);
		return movie;
	}

	public void editMovie(String _movieID, String name, String movieDateString, String _duration)
			throws MovieValidationException, MovieNotFoundException {
		// duration in minutes // imagine "movie theater has a movie has one minute in
		// duration"

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int duration = 0;
		int movieID = -1;

		try {
			movieID = Integer.parseInt(_movieID);
		} catch (NumberFormatException e) {
			throw new MovieValidationException("MovieID not valid!!");
		}

		if (name.length() <= 0)
			throw new MovieValidationException("Name required!!");

		if (movieDateString == null)
			throw new MovieValidationException("MovieDate required!!");

		try {
			duration = Integer.parseInt(_duration);
		} catch (NumberFormatException e) {
			throw new MovieValidationException("Duration not valid!!");
		}

		if (duration <= 0)
			throw new MovieValidationException("Duration required!!");

		Date movieDate = null;

		try {
			movieDate = format.parse(movieDateString);
		} catch (ParseException e) {
			throw new MovieValidationException("MovieDate not valid!!");
		}

		Movie movie = this.getMovie(movieID);
		if (movie != null) {
			movie.MovieID = GlobalHelper.getNextMovieID();
			movie.Duration = duration;
			movie.MovieDate = movieDate;
			movie.Name = name;
		} else
			throw new MovieNotFoundException();

	}

	public ArrayList<Movie> listMovies() {
		return this.movies;
	}

	public Movie getMovie(int movieID) {
		for (int i = 0; i < this.movies.size(); i++)
			if (this.movies.get(i).MovieID == movieID)
				return this.movies.get(i);
		return null;
	}

	public Movie getMovie(String _movieID) {
		int movieID = -1;

		try {
			movieID = Integer.parseInt(_movieID);
		} catch (NumberFormatException e) {
		}
		
		for (int i = 0; i < this.movies.size(); i++)
			if (this.movies.get(i).MovieID == movieID)
				return this.movies.get(i);
		return null;
	}

	public int getIndexMovie(int movieID) {
		for (int i = 0; i < this.movies.size(); i++)
			if (this.movies.get(i).MovieID == movieID)
				return i;

		return -1;
	}

	public void deleteMovie(String _movieID) throws MovieNotFoundException, MovieValidationException {
		int movieID = -1;

		try {
			movieID = Integer.parseInt(_movieID);
		} catch (NumberFormatException e) {
			throw new MovieValidationException("MovieID not valid!!");
		}

		int indexMovie = this.getIndexMovie(movieID);

		if (indexMovie > -1) {
			CategoryMovieService.getInstance().removeCategories(this.movies.get(indexMovie));
			PriceService.getInstance().deletePriceByMovie(this.movies.get(indexMovie));
			this.movies.remove(indexMovie);
		}
		else
			throw new MovieNotFoundException();
	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("movies.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.movies);
		stream.close();
		objectStream.close();

	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("movies.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.movies = (ArrayList<Movie>) obj;
		stream.close();
		objectStream.close();

	}
}
