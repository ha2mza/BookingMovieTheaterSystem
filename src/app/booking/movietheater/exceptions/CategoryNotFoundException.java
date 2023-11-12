package app.booking.movietheater.exceptions;

public class CategoryNotFoundException extends Exception {
	public CategoryNotFoundException() {
		super("Category not found");
	}
}
