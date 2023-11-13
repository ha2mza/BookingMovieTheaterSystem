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
import app.booking.movietheater.exceptions.CategoryMovieValidationException;
import app.booking.movietheater.exceptions.CategoryValidationException;

public class CategoryMovieService {
	private static CategoryMovieService instance = null;
	private ArrayList<CategoryMovie> categoryMovies;

	private CategoryMovieService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.categoryMovies = new ArrayList<>();
		}
	}

	public static CategoryMovieService getInstance() {
		if (instance == null)
			instance = new CategoryMovieService();

		return instance;
	}

	public ArrayList<Movie> getMovies(String categoryID) {
		ArrayList<Movie> movies = new ArrayList<Movie>();

		for (int i = 0; i < categoryMovies.size(); i++) {
			if (categoryMovies.get(i).Category.CategoryID.equals(categoryID))
				movies.add(this.categoryMovies.get(i).Movie);
		}
		return movies;
	}

	public ArrayList<Category> getCategories(int MovieID) {
		ArrayList<Category> categories = new ArrayList<Category>();

		for (int i = 0; i < categoryMovies.size(); i++) {
			if (categoryMovies.get(i).Movie.MovieID == MovieID)
				categories.add(this.categoryMovies.get(i).Category);
		}
		return categories;
	}

	public int getIndexCategoryMovie(Category category, Movie movie) {
		for (int i = 0; i < categoryMovies.size(); i++) {
			if (categoryMovies.get(i).Movie.MovieID == movie.MovieID && 
					categoryMovies.get(i).Category.CategoryID.equals(category.CategoryID))
				return i;
		}
		
		return -1;
	}
	

	public void removeCategoryMovie(Category category, Movie movie) {
		int indexCategoriMovie  =  this.getIndexCategoryMovie(category, movie);
		if(indexCategoriMovie > -1)
			this.categoryMovies.remove(indexCategoriMovie);
	}


	public void setMovies(Category category, ArrayList<Movie> movies) {
		for (int i = 0; i < movies.size(); i++) {
			if(this.getIndexCategoryMovie(category, movies.get(i)) <= -1)
				this.categoryMovies.add(new CategoryMovie(category, movies.get(i)));
		}
	}
	

	public void setCategories(Movie movie, ArrayList<Category> categories) {
		for (int i = 0; i < categories.size(); i++) {
			if(this.getIndexCategoryMovie(categories.get(i), movie) <= -1)
				this.categoryMovies.add(new CategoryMovie(categories.get(i), movie));
		}
	}

	public void removeMovies(Category category, ArrayList<Movie> movies) {
		for (int i = 0; i < movies.size(); i++) {
			this.removeCategoryMovie(category, movies.get(i));
		}
	}


	public void removeCategories(Movie movie) {
		for (int i = 0; i < this.categoryMovies.size(); i++) {
			if(movie.MovieID == this.categoryMovies.get(i).Movie.MovieID)
				this.categoryMovies.remove(i);
		}
	}
	
	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("categoryMovies.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.categoryMovies);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("categoryMovies.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.categoryMovies = (ArrayList<CategoryMovie>) obj;
		stream.close();
		objectStream.close();

	}
}
