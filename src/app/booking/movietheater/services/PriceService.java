package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.booking.movietheater.entities.Price;
import app.booking.movietheater.entities.GlobalHelper;
import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.exceptions.PriceValidationException;
import app.booking.movietheater.exceptions.MovieTheaterValidationException;
import app.booking.movietheater.exceptions.MovieValidationException;
import app.booking.movietheater.exceptions.PriceNotFoundException;

public class PriceService {
	private static PriceService instance = null;
	private ArrayList<Price> prices;

	private PriceService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.prices = new ArrayList<>();
		}
	}

	public static PriceService getInstance() {
		if (instance == null)
			instance = new PriceService();

		return instance;
	}

	public ArrayList<Price> listPrices() {
		return prices;
	}

	public void createPrice(String name, String _price, Movie movie) throws PriceValidationException {
		if (movie == null)
			throw new PriceValidationException("Movie required!!");

		if (name.length() <= 0)
			throw new PriceValidationException("Name required!!");
		float amount = 0;

		try {
			amount = Float.parseFloat(_price);
		} catch (NumberFormatException e) {
			throw new PriceValidationException("Price not valid!!");
		}

		if (amount <= 0)
			throw new PriceValidationException("Price required!!");

		Price price = new Price();
		price.Name = name;
		price.PriceID = GlobalHelper.getNextPriceID();
		price.Movie = movie;
		price.Price = amount;
		prices.add(price);
	}

	public void editPrice(int PriceID, String name, String _price, Movie movie)
			throws PriceValidationException, PriceNotFoundException {
		if (movie == null)
			throw new PriceValidationException("Movie required!!");

		if (name.length() <= 0)
			throw new PriceValidationException("Name required!!");

		float amount = 0;

		try {
			amount = Float.parseFloat(_price);
		} catch (NumberFormatException e) {
			throw new PriceValidationException("Price not valid!!");
		}

		if (amount <= 0)
			throw new PriceValidationException("Price required!!");

		Price price = this.getPrice(PriceID);
		if (price != null) {
			price.Name = name;
			price.Movie = movie;
			price.Price = amount;
		} else
			throw new PriceNotFoundException();
	}

	public Price getPrice(int PriceID) {
		for (int i = 0; i < prices.size(); i++) {
			if (prices.get(i).PriceID == (PriceID))
				return prices.get(i);
		}
		return null;
	}

	public Price getPrice(String _PriceID) {
		int PriceID = -1;
		try {
			PriceID = Integer.parseInt(_PriceID);
		} catch (NumberFormatException e) {
		}

		for (int i = 0; i < prices.size(); i++) {
			if (prices.get(i).PriceID == (PriceID))
				return prices.get(i);
		}
		return null;
	}

	public ArrayList<Price> getPrices(Movie movie) {
		ArrayList<Price> prices = new ArrayList<>();
		if (movie != null)
			for (int i = 0; i < this.prices.size(); i++)
				if (this.prices.get(i).Movie.MovieID == movie.MovieID)
					prices.add(this.prices.get(i));

		return prices;
	}

	public int getIndexPrice(int PriceID) {
		for (int i = 0; i < prices.size(); i++)
			if (prices.get(i).PriceID == (PriceID))
				return i;
		return -1;
	}

	public void deletePrice(int PriceID) throws PriceNotFoundException {
		int indexPrice = this.getIndexPrice(PriceID);
		if (indexPrice > -1)
			prices.remove(indexPrice);
		else
			throw new PriceNotFoundException();

	}

	public void deletePriceByMovie(Movie movie) {
		this.getPrices(movie).forEach((price) -> {
			try {
				this.deletePrice(price.PriceID);
			} catch (PriceNotFoundException e) {

			}
		});

	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("prices.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.prices);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("prices.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.prices = (ArrayList<Price>) obj;
		stream.close();
		objectStream.close();

	}

}
