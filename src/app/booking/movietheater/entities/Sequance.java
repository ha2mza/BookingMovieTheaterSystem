package app.booking.movietheater.entities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Sequance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3975852139835484398L;
	private static Sequance instance;
	public int MovieID = 0;
	public int MovieTheaterID = 0;
	public int RoomID = 0;
	public int PriceID = 0;
	public int SessionID = 0;

	public static Sequance getInstance() {
		if (instance == null)
		{
			try {
				readData();
			} catch (Exception e) {
				instance = new Sequance();
			}
			
		}

		return instance;
	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("sequance.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(instance);
		stream.close();
		objectStream.close();
	}

	public static void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("sequance.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof Sequance)
			instance = (Sequance) obj;
		stream.close();
		objectStream.close();

	}
}
