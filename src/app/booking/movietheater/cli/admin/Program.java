package app.booking.movietheater.cli.admin;

import java.io.IOException;

import java.util.Scanner;

import app.booking.movietheater.entities.Sequance;
import app.booking.movietheater.services.CategoryMovieService;
import app.booking.movietheater.services.CategoryService;
import app.booking.movietheater.services.MovieService;
import app.booking.movietheater.services.MovieTheaterService;
import app.booking.movietheater.services.PriceService;
import app.booking.movietheater.services.RoomService;
import app.booking.movietheater.services.SessionService;
import app.booking.movietheater.threads.UpdateSessionStateRunnable;

public class Program {
	private static Program _program = null;

	public static Program console() {
		if (_program != null)
			return _program;

		return new Program();
	}

	public static void saveData() {
		try {
			CategoryService.getInstance().saveData();
			MovieService.getInstance().saveData();
			MovieTheaterService.getInstance().saveData();
			SessionService.getInstance().saveData();
			RoomService.getInstance().saveData();
			PriceService.getInstance().saveData();
			CategoryMovieService.getInstance().saveData();
			Sequance.getInstance().saveData();
			System.out.println("Data has been saved");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String readLine(String msg) {
		Scanner console = new Scanner(System.in);
		System.out.print(msg);
		return console.nextLine();
	}

	public static void main(String[] args) {
		Thread thread = new Thread(new UpdateSessionStateRunnable());
		thread.start();

		Boolean _continue = true;
		Program console = Program.console();
		while (_continue) {
			System.out.println("Welcome admin");
			System.out.println("=======================");
			System.out.println("1. Manage Categories");
			System.out.println("2. Manage Movies");
			System.out.println("3. Manage MovieTheater");
			System.out.println("4. Manage Session");
			System.out.println("5. Exit App");
			System.out.println("=======================");
			String choice = console.readLine("Put you choice[1-4]: ");

			switch (choice) {
			case "1":
				manageCategoryService();
				choice = console.readLine("Main Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "2":
				manageMovieService();
				choice = console.readLine("Main Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "3":
				manageMovieTheaterService();
				choice = console.readLine("Main Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "4":
				manageSessionService();
				choice = console.readLine("Main Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "5":
				saveData();
				System.out.println("\n\nSeeyou later :) boss");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice!!");
				break;
			}
		}
		saveData();
		System.out.println("\n\nSeeyou later :) boss");

	}

	public static void manageCategoryService() {
		Program console = Program.console();
		Boolean _continue = true;

		while (_continue) {
			System.out.println("Category Management: ");
			System.out.println("=======================");
			System.out.println("1. List Category");
			System.out.println("2. Add Category");
			System.out.println("3. Edit Category");
			System.out.println("4. Delete Category");
			System.out.println("5. Exit App");
			System.out.println("=======================");
			String choice = console.readLine("Put you choice[1-4]: ");

			switch (choice) {
			case "1":
				CategoryHelper.listCategories();
				choice = console.readLine("Category Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "2":
				CategoryHelper.addCategory();
				choice = console.readLine("Category Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "3":
				CategoryHelper.editCategory();
				choice = console.readLine("Category Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "4":
				CategoryHelper.deleteCategory();
				choice = console.readLine("Category Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;

			case "5":
				saveData();
				System.out.println("\n\nSeeyou later :) boss");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice!!");
				break;
			}
		}
	}

	public static void manageMovieService() {
		Program console = Program.console();
		Boolean _continue = true;

		while (_continue) {
			System.out.println("Movie Management: ");
			System.out.println("=======================");
			System.out.println("1. List Movie");
			System.out.println("2. Add Movie");
			System.out.println("3. Edit Movie");
			System.out.println("4. Delete Movie");
			System.out.println("5. Exit App");
			System.out.println("=======================");
			String choice = console.readLine("Put you choice[1-4]: ");

			switch (choice) {
			case "1":
				MovieHelper.listMovies();
				choice = console.readLine("Movie Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "2":
				MovieHelper.addMovie();
				choice = console.readLine("Movie Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;


			case "3":
				MovieHelper.editMovie();
				choice = console.readLine("Movie Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;


			case "4":
				MovieHelper.removeMovie();
				choice = console.readLine("Movie Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;

			case "5":
				saveData();
				System.out.println("\n\nSeeyou later :) boss");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice!!");
				break;
			}
		}
	}

	public static void manageMovieTheaterService() {
		Program console = Program.console();
		Boolean _continue = true;

		while (_continue) {
			System.out.println("MovieTheater Management: ");
			System.out.println("=======================");
			System.out.println("1. List MovieTheater");
			System.out.println("2. Add MovieTheater");
			System.out.println("3. Edit MovieTheater");
			System.out.println("4. Delete MovieTheater");
			System.out.println("5. Exit App");
			System.out.println("=======================");
			String choice = console.readLine("Put you choice[1-4]: ");

			switch (choice) {
			case "1":
				MovieTheaterHelper.listMovieTheaters();
				choice = console.readLine("MovieTheater Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "2":
				MovieTheaterHelper.createMovieTheater();
				choice = console.readLine("MovieTheater Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;


			case "3":
				MovieTheaterHelper.editMovieTheater();
				choice = console.readLine("MovieTheater Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;


			case "4":
				MovieTheaterHelper.removeMovieTheater();
				choice = console.readLine("MovieTheater Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;

			case "5":
				saveData();
				System.out.println("\n\nSeeyou later :) boss");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice!!");
				break;
			}
		}
	}

	public static void manageSessionService() {
		Program console = Program.console();
		Boolean _continue = true;

		while (_continue) {
			System.out.println("Session Management: ");
			System.out.println("=======================");
			System.out.println("1. List Session");
			System.out.println("2. Add Session");
			System.out.println("3. Edit Session");
			System.out.println("4. Delete Session");
			System.out.println("5. Exit App");
			System.out.println("=======================");
			String choice = console.readLine("Put you choice[1-4]: ");

			switch (choice) {
			case "1":
				SessionHelper.listSessions();
				choice = console.readLine("Session Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "2":
				SessionHelper.createSession();
				choice = console.readLine("Session Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;


			case "3":
				SessionHelper.editSession();
				choice = console.readLine("Session Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;


			case "4":
				SessionHelper.removeSession();
				choice = console.readLine("Session Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;

			case "5":
				saveData();
				System.out.println("\n\nSeeyou later :) boss");
				System.exit(0);
				break;

			default:
				System.out.println("Invalid choice!!");
				break;
			}
		}
	}

}
