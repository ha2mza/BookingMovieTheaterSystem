package app.booking.movietheater.cli.client;

import java.io.IOException;
import java.util.Scanner;

import app.booking.movietheater.entities.Sequance;
import app.booking.movietheater.services.BookingPlaceService;
import app.booking.movietheater.services.BookingTicketService;
import app.booking.movietheater.services.CategoryMovieService;
import app.booking.movietheater.services.CategoryService;
import app.booking.movietheater.services.MovieService;
import app.booking.movietheater.services.MovieTheaterService;
import app.booking.movietheater.services.PriceService;
import app.booking.movietheater.services.RoomService;
import app.booking.movietheater.services.SessionService;

public class Program {


	public static void saveData() {
		try {
			BookingPlaceService.getInstance().saveData();
			BookingTicketService.getInstance().saveData();
			Sequance.getInstance().saveData();
			System.out.println("Data has been saved");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static Program _program = null;

	public static Program console() {
		if (_program != null)
			return _program;

		return new Program();
	}

	public String readLine(String msg) {
		Scanner console = new Scanner(System.in);
		System.out.print(msg);
		return console.nextLine();
	}

	public static void main(String[] args) {
		// lets build finally client CLI

		Boolean _continue = true;
		Program console = Program.console();
		while (_continue) {
			System.out.println("Welcome to our MovieTheater System\n");
			System.out.println("== you can booking ticket without go to our magasins ==");
			System.out.println("1. New Booking Ticket");
			System.out.println("2. Exists Booking Ticket");
			System.out.println("3. Exit");
			System.out.println("=======================");
			String choice = console.readLine("Put you choice[1-2]: ");

			switch (choice) {
			case "1":
				BookingTicketHelper.newBookingTicket();
				choice = console.readLine("Main Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "2":
				BookingTicketHelper.displayBookingTicket();
				choice = console.readLine("Main Menu - Continue [Y/N]: ");
				if (!choice.toLowerCase().trim().equals("y"))
					_continue = false;
				break;
			case "3":
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
	}

}
