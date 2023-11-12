package app.booking.movietheater.entities;

import java.io.Serializable;

public class MovieMovieTheater implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9116646313356979588L;
	public MovieTheater MovieTheater;
	public Movie Movie;
	

	public MovieMovieTheater(Movie movie, MovieTheater movieTheater) {
		this.Movie =movie;
		this.MovieTheater = movieTheater;
	}
}
