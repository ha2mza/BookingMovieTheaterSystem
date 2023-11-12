package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.booking.movietheater.entities.Category;
import app.booking.movietheater.entities.CategoryMovie;
import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.entities.MovieMovieTheater;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.exceptions.CategoryMovieValidationException;
import app.booking.movietheater.exceptions.MovieMovieTheaterValidationException;

public class MovieMovieTheaterService {
	private static MovieMovieTheaterService instance = null;
	private ArrayList<MovieMovieTheater> movieMovieTheaters;

	private MovieMovieTheaterService() {
		try {
			this.readData();
		}catch (Exception e) {
			this.movieMovieTheaters = new ArrayList<>();
		}
	}

	public static MovieMovieTheaterService getInstance() {
		if (instance == null)
			instance = new MovieMovieTheaterService();

		return instance;
	}
	

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("movieMovieTheaters.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.movieMovieTheaters);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("movieMovieTheaters.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.movieMovieTheaters = (ArrayList<MovieMovieTheater>) obj;
		stream.close();
		objectStream.close();

	}
	
	
	
	
	public ArrayList<MovieTheater> getMovieTheaters(int movieID) {
		ArrayList<MovieTheater> movieTheaters = new ArrayList<MovieTheater>();

		for (int i = 0; i < movieMovieTheaters.size(); i++) {
			if (movieMovieTheaters.get(i).Movie.MovieID == (movieID))
				movieTheaters.add(this.movieMovieTheaters.get(i).MovieTheater);
		}
		return movieTheaters;
	}

	public ArrayList<Movie> getMovies(int MovieTheaterID) {
		ArrayList<Movie> movies = new ArrayList<Movie>();

		for (int i = 0; i < movieMovieTheaters.size(); i++) {
			if (movieMovieTheaters.get(i).MovieTheater.MovieTheaterID == MovieTheaterID)
				movies.add(this.movieMovieTheaters.get(i).Movie);
		}
		return movies;
	}

	public int getIndexMovieMovieTheater(Movie movie, MovieTheater movieTheater) {
		for (int i = 0; i < movieMovieTheaters.size(); i++) {
			if (movieMovieTheaters.get(i).MovieTheater.MovieTheaterID == movieTheater.MovieTheaterID && 
					movieMovieTheaters.get(i).Movie.MovieID == (movie.MovieID))
				return i;
		}
		
		return -1;
	}
	

	public void removeMovieMovieTheater(Movie movie, MovieTheater movieTheater) {
		int indexCategoriMovieTheater  =  this.getIndexMovieMovieTheater(movie, movieTheater);
		if(indexCategoriMovieTheater > -1)
			this.movieMovieTheaters.remove(indexCategoriMovieTheater);
	}


	public void setMovieTheaters(Movie movie, ArrayList<MovieTheater> movieTheaters) throws MovieMovieTheaterValidationException {
		for (int i = 0; i < movieTheaters.size(); i++) {
			if(this.getIndexMovieMovieTheater(movie, movieTheaters.get(i)) <= -1)
				this.movieMovieTheaters.add(new MovieMovieTheater(movie, movieTheaters.get(i)));
		}
	}
	

	public void setMovies(MovieTheater movieTheater, ArrayList<Movie> movies) throws MovieMovieTheaterValidationException {
		for (int i = 0; i < movies.size(); i++) {
			if(this.getIndexMovieMovieTheater(movies.get(i), movieTheater) <= -1)
				this.movieMovieTheaters.add(new MovieMovieTheater(movies.get(i), movieTheater));
		}
	}

	public void removeMovieTheaters(Movie movie, ArrayList<MovieTheater> movieTheaters) {
		for (int i = 0; i < movieTheaters.size(); i++) {
			this.removeMovieMovieTheater(movie, movieTheaters.get(i));
		}
	}


	public void removeMovies(MovieTheater movieTheater, ArrayList<Movie> movies) {
		for (int i = 0; i < movies.size(); i++) {
			this.removeMovieMovieTheater(movies.get(i), movieTheater);
		}
	}
	
}
