package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.booking.movietheater.entities.Room;
import app.booking.movietheater.entities.GlobalHelper;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.exceptions.RoomValidationException;
import app.booking.movietheater.exceptions.PriceNotFoundException;
import app.booking.movietheater.exceptions.PriceValidationException;
import app.booking.movietheater.exceptions.RoomNotFoundException;

public class RoomService {
	private static RoomService instance = null;
	private ArrayList<Room> rooms;

	private RoomService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.rooms = new ArrayList<>();
		}
	}

	public static RoomService getInstance() {
		if (instance == null)
			instance = new RoomService();

		return instance;
	}

	public ArrayList<Room> listRooms() {
		return rooms;
	}

	public void createRoom(String name, String _capacity, MovieTheater movieTheater) throws RoomValidationException {

		int capacity = 0;

		try {
			capacity = Integer.parseInt(_capacity);
		} catch (NumberFormatException e) {
			throw new RoomValidationException("Capacity not valid!!");
		}

		if (capacity <= 0)
			throw new RoomValidationException("Room capacity must be great then 0!!");

		if (movieTheater == null)
			throw new RoomValidationException("MovieTheater required!!");

		if (name.length() <= 0)
			throw new RoomValidationException("Name required!!");

		Room room = new Room();
		room.Name = name;
		room.Capacity = capacity;
		room.RoomID = GlobalHelper.getNextRoomID();
		room.MovieTheater = movieTheater;
		rooms.add(room);
	}

	public void editRoom(int RoomID, String name, String _capacity, MovieTheater movieTheater)
			throws RoomValidationException, RoomNotFoundException {
		int capacity = 0;

		try {
			capacity = Integer.parseInt(_capacity);
		} catch (NumberFormatException e) {
			throw new RoomValidationException("Capacity not valid!!");
		}

		if (capacity <= 0)
			throw new RoomValidationException("Room capacity must be great then 0");

		if (movieTheater == null)
			throw new RoomValidationException("MovieTheater required!!");

		if (name.length() <= 0)
			throw new RoomValidationException("Name required!!");

		Room room = this.getRoom(RoomID);
		if (room != null) {
			room.Name = name;
			room.Capacity = capacity;
			room.MovieTheater = movieTheater;
		} else
			throw new RoomNotFoundException();
	}

	public Room getRoom(int RoomID) {
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).RoomID == (RoomID))
				return rooms.get(i);
		}
		return null;
	}

	public Room getRoom(MovieTheater movieTheater, String _RoomID) {
		int RoomID = -1;

		try {
			RoomID = Integer.parseInt(_RoomID);
		} catch (NumberFormatException e) {
		}
		if (movieTheater != null)
			for (int i = 0; i < rooms.size(); i++) {
				if (rooms.get(i).MovieTheater.MovieTheaterID == movieTheater.MovieTheaterID
						&& rooms.get(i).RoomID == (RoomID))
					return rooms.get(i);
			}
		return null;
	}

	public ArrayList<Room> getRooms(MovieTheater movieTheater) {
		ArrayList<Room> rooms = new ArrayList<>();
		if (movieTheater != null)
			for (int i = 0; i < this.rooms.size(); i++)
				if (this.rooms.get(i).MovieTheater.MovieTheaterID == movieTheater.MovieTheaterID)
					rooms.add(this.rooms.get(i));

		return rooms;
	}

	public int getIndexRoom(int RoomID) {
		for (int i = 0; i < rooms.size(); i++)
			if (rooms.get(i).RoomID == (RoomID))
				return i;
		return -1;
	}

	public void deleteRoom(int RoomID) throws RoomNotFoundException {
		int indexRoom = this.getIndexRoom(RoomID);
		if (indexRoom > -1)
			rooms.remove(indexRoom);
		else
			throw new RoomNotFoundException();

	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("rooms.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.rooms);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("rooms.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.rooms = (ArrayList<Room>) obj;
		stream.close();
		objectStream.close();

	}

	public void deleteRoomByMovieTheater(MovieTheater movieTheater) {
		this.getRooms(movieTheater).forEach((room) -> {
			try {
				this.deleteRoom(room.RoomID);
			} catch (RoomNotFoundException e) {

			}
		});

	}

}
