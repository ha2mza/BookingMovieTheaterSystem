package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.booking.movietheater.entities.GlobalHelper;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.exceptions.MovieTheaterNotFoundException;
import app.booking.movietheater.exceptions.MovieTheaterValidationException;
import app.booking.movietheater.exceptions.MovieValidationException;
import app.booking.movietheater.exceptions.RoomValidationException;

public class MovieTheaterService {
	private static MovieTheaterService instance = null;
	private ArrayList<MovieTheater> movieTheaters;

	private MovieTheaterService() {

		try {
			this.readData();
		} catch (Exception e) {
			this.movieTheaters = new ArrayList<>();
		}
	}

	public static MovieTheaterService getInstance() {
		if (instance == null)
			instance = new MovieTheaterService();

		return instance;
	}

	public MovieTheaterService(ArrayList<MovieTheater> movieTheaters) {
		this.movieTheaters = movieTheaters;
	}

	public ArrayList<MovieTheater> listMovieTheaters() {
		return movieTheaters;
	}

	public MovieTheater createMovieTheater(String name, String address) throws MovieTheaterValidationException {
		MovieTheater movieTheater = new MovieTheater();
		if (name.length() < 0)
			throw new MovieTheaterValidationException("Name required!!");
		if (address.length() < 0)
			throw new MovieTheaterValidationException("Address required!!");
		movieTheater.Name = name;
		movieTheater.Address = address;
		movieTheater.MovieTheaterID = GlobalHelper.getNextMovieTheaterID();
		movieTheaters.add(movieTheater);

		return movieTheater;
	}

	public void editMovieTheater(String _MovieTheaterID, String name, String address)
			throws MovieTheaterValidationException, MovieTheaterNotFoundException {
		int MovieTheaterID = -1;
		try {
			MovieTheaterID = Integer.parseInt(_MovieTheaterID);
		} catch (NumberFormatException e) {
			throw new MovieTheaterValidationException("MovieTheaterID not valid!!");
		}
		if (name.length() < 0)
			throw new MovieTheaterValidationException("Name required!!");
		if (address.length() < 0)
			throw new MovieTheaterValidationException("Address required!!");

		MovieTheater movieTheater = this.getMovieTheater(MovieTheaterID);
		if (movieTheater != null) {
			movieTheater.Name = name;
			movieTheater.Address = address;
		} else
			throw new MovieTheaterNotFoundException();
	}

	public MovieTheater getMovieTheater(int movieTheaterID) {
		for (int i = 0; i < movieTheaters.size(); i++)
			if (movieTheaters.get(i).MovieTheaterID == movieTheaterID)
				return movieTheaters.get(i);
		return null;
	}

	public MovieTheater getMovieTheater(String _movieTheaterID) {
		int movieTheaterID = -1;

		try {
			movieTheaterID = Integer.parseInt(_movieTheaterID);
		} catch (NumberFormatException e) {
		}
		
		for (int i = 0; i < movieTheaters.size(); i++)
			if (movieTheaters.get(i).MovieTheaterID == movieTheaterID)
				return movieTheaters.get(i);
		return null;
	}

	public int getIndexMovieTheater(int movieTheaterID) {
		for (int i = 0; i < movieTheaters.size(); i++)
			if (movieTheaters.get(i).MovieTheaterID == movieTheaterID)
				return i;
		return -1;
	}

	public void deleteMovieTheater(String _movieTheaterID) throws MovieTheaterNotFoundException, MovieTheaterValidationException {
		int movieTheaterID = -1;

		try {
			movieTheaterID = Integer.parseInt(_movieTheaterID);
		} catch (NumberFormatException e) {
			throw new MovieTheaterValidationException("MovieTheaterID not valid!!");
		}
		int indexMovieTheater = this.getIndexMovieTheater(movieTheaterID);
		if (indexMovieTheater > -1) {
			RoomService.getInstance().deleteRoomByMovieTheater(this.movieTheaters.get(indexMovieTheater));
			movieTheaters.remove(indexMovieTheater);
			
			
		}
		else
			throw new MovieTheaterNotFoundException();

	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("movieTheaters.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.movieTheaters);
		stream.close();
		objectStream.close();

	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("movieTheaters.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.movieTheaters = (ArrayList<MovieTheater>) obj;
		stream.close();
		objectStream.close();

	}
}
