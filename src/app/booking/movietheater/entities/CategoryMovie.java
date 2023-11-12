package app.booking.movietheater.entities;

import java.io.Serializable;

public class CategoryMovie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3412748666408211703L;
	public Category Category;
	public Movie Movie;

	public CategoryMovie(Category category, Movie movie) {
		this.Category = category;
		this.Movie = movie;
	}
}
