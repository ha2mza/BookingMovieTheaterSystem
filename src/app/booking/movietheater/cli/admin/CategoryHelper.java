package app.booking.movietheater.cli.admin;

import java.util.ArrayList;

import app.booking.movietheater.entities.Category;
import app.booking.movietheater.exceptions.CategoryNotFoundException;
import app.booking.movietheater.exceptions.CategoryValidationException;
import app.booking.movietheater.services.CategoryService;

public class CategoryHelper {
	private static CategoryService categoryService = CategoryService.getInstance();

	public static void listCategories() {
		ArrayList<Category> categories = categoryService.listCategories();

		for (int i = 0; i < categories.size(); i++) {
			System.out.println(categories.get(i));
		}
	}

	public static void addCategory() {
		Boolean _continue = true;
		Program console = Program.console();

		while (_continue) {
			System.out.println("Form Add Category: \n\n");
			String name = console.readLine("Put Name: ");
			String categoryID = console.readLine("Put CategoryID: ");
			try {
				categoryService.createCategory(name, categoryID);
				_continue = false;
			} catch (CategoryValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}

	public static void editCategory() {
		Boolean _continue = true;
		Program console = Program.console();

		while (_continue) {
			try {
				System.out.println("Form Edit Category: \n\n");
				String categoryID = console.readLine("Put CategoryID: ");
				Category category = categoryService.getCategoryOrException(categoryID);

				String name = console.readLine("Put Name (" + category.Name + "): ");
				categoryService.editCategory(categoryID, name);
				_continue = false;
			} catch (CategoryValidationException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			} catch (CategoryNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void deleteCategory() {
		Program console = Program.console();
		try {
			System.out.println("Form Delete Category: \n\n");
			String categoryID = console.readLine("Put CategoryID: ");
			categoryService.deleteCategory(categoryID);
		} catch (CategoryNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}
}
