package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.booking.movietheater.entities.Category;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.exceptions.CategoryNotFoundException;
import app.booking.movietheater.exceptions.CategoryValidationException;

public class CategoryService {
	private static CategoryService instance = null;
	private ArrayList<Category> categories;

	private CategoryService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.categories = new ArrayList<>();
		}
	}

	public static CategoryService getInstance() {
		if (instance == null)
			instance = new CategoryService();

		return instance;
	}

	public ArrayList<Category> listCategories() {
		return categories;
	}

	public void createCategory(String name, String CategoryID) throws CategoryValidationException {
		if (name.length() <= 0)
			throw new CategoryValidationException("Name required!!");

		if (CategoryID.length() <= 0)
			throw new CategoryValidationException("CategoryID required!!");

		if (this.getIndexCategory(CategoryID) > -1)
			throw new CategoryValidationException("CategoryID already exists!!");

		Category category = new Category();
		category.Name = name;
		category.CategoryID = CategoryID;
		categories.add(category);
	}

	public void editCategory(String CategoryID, String name)
			throws CategoryValidationException, CategoryNotFoundException {
		if (name.length() <= 0)
			throw new CategoryValidationException("Name required!!");

		Category category = this.getCategory(CategoryID);
		if (category != null) {
			category.Name = name;
		} else
			throw new CategoryNotFoundException();
	}

	public Category getCategory(String CategoryID) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).CategoryID.equals(CategoryID))
				return categories.get(i);
		}
		return null;
	}
	

	public Category getCategoryOrException(String CategoryID) throws CategoryNotFoundException {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).CategoryID.equals(CategoryID))
				return categories.get(i);
		}
		throw new CategoryNotFoundException();
	}
	
	

	public int getIndexCategory(String CategoryID) {
		for (int i = 0; i < categories.size(); i++)
			if (categories.get(i).CategoryID.equals(CategoryID))
				return i;
		return -1;
	}

	public void deleteCategory(String CategoryID) throws CategoryNotFoundException {
		int indexCategory = this.getIndexCategory(CategoryID);
		if (indexCategory > -1)
			categories.remove(indexCategory);
		else
			throw new CategoryNotFoundException();

	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("categories.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.categories);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("categories.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.categories = (ArrayList<Category>) obj;
		stream.close();
		objectStream.close();

	}

}
