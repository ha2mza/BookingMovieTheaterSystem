package app.booking.movietheater.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.booking.movietheater.entities.Session;
import app.booking.movietheater.entities.SessionState;
import app.booking.movietheater.entities.GlobalHelper;
import app.booking.movietheater.entities.Movie;
import app.booking.movietheater.entities.MovieTheater;
import app.booking.movietheater.entities.Room;
import app.booking.movietheater.exceptions.SessionValidationException;
import app.booking.movietheater.exceptions.MovieValidationException;
import app.booking.movietheater.exceptions.SessionNotFoundException;

public class SessionService {
	private static SessionService instance = null;
	private ArrayList<Session> sessions;

	private SessionService() {
		try {
			this.readData();
		} catch (Exception e) {
			this.sessions = new ArrayList<>();
		}
	}

	public static SessionService getInstance() {
		if (instance == null)
			instance = new SessionService();

		return instance;
	}

	public ArrayList<Session> listSessions() {
		return sessions;
	}
	
	
	@SuppressWarnings("deprecation")
	public synchronized void updateSessions(Date time){
		for (int i = 0; i < sessions.size(); i++) {
			Session session = sessions.get(i);
			if (session.sessionState == SessionState.PENDING && session.Time.after(time)) {
				Date sessionEndDate  = (Date)session.Time.clone();
				sessionEndDate.setMinutes(session.Time.getMinutes() + session.Movie.Duration);
				if(sessionEndDate.after(time)) {
					session.sessionState = SessionState.END;
				}
				else {
					session.sessionState = SessionState.START;
				}
			}
			else if (session.sessionState == SessionState.START) {
				Date sessionEndDate  = (Date)session.Time.clone();
				sessionEndDate.setMinutes(session.Time.getMinutes() + session.Movie.Duration);
				if(sessionEndDate.after(time)) {
					session.sessionState = SessionState.END;
				}
			}
		}
	}

	public void createSession(String name, String _time, Movie movie, MovieTheater movieTheater, Room room)
			throws SessionValidationException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date time = null;
		if (movie == null)
			throw new SessionValidationException("Movie required!!");

		if (movieTheater == null)
			throw new SessionValidationException("MovieTheater required!!");

		if (room == null)
			throw new SessionValidationException("Room required!!");


		if (_time == null)
			throw new SessionValidationException("Time required!!");
		
		try {
			time = format.parse(_time);
		} catch (ParseException e) {
			throw new SessionValidationException("SessionDate not valid!!");
		}
		

		if (name.length() <= 0)
			throw new SessionValidationException("Name required!!");

		
		Session session = new Session();
		session.Name = name;
		session.SessionID = GlobalHelper.getNextSessionID();
		session.Movie = movie;
		session.MovieTheater = movieTheater;
		session.Room = room;
		session.Time = time;
		sessions.add(session);
	}

	public void editSession(int SessionID, String name, Date time, Movie movie, MovieTheater movieTheater, Room room)
			throws SessionValidationException, SessionNotFoundException {
		if (movie == null)
			throw new SessionValidationException("Movie required!!");

		if (movieTheater == null)
			throw new SessionValidationException("MovieTheater required!!");

		if (room == null)
			throw new SessionValidationException("Room required!!");

		if (time == null)
			throw new SessionValidationException("Time required!!");

		if (name.length() <= 0)
			throw new SessionValidationException("Name required!!");

		Session session = this.getSession(SessionID);
		if (session != null) {
			session.Name = name;
			session.Movie = movie;
			session.MovieTheater = movieTheater;
			session.Room = room;
			session.Time = time;
		} else
			throw new SessionNotFoundException();
	}

	public Session getSession(int SessionID) {
		for (int i = 0; i < sessions.size(); i++) {
			if (sessions.get(i).SessionID == (SessionID))
				return sessions.get(i);
		}
		return null;
	}

	public ArrayList<Session> getSessions(Movie movie) {
		ArrayList<Session> sessions = new ArrayList<>();
		if (movie != null)
			for (int i = 0; i < sessions.size(); i++)
				if (sessions.get(i).Movie.MovieID == movie.MovieID)
					sessions.add(sessions.get(i));

		return sessions;
	}

	public int getIndexSession(int SessionID) {
		for (int i = 0; i < sessions.size(); i++)
			if (sessions.get(i).SessionID == (SessionID))
				return i;
		return -1;
	}

	public void deleteSession(int SessionID) throws SessionNotFoundException {
		int indexSession = this.getIndexSession(SessionID);
		if (indexSession > -1)
			sessions.remove(indexSession);
		else
			throw new SessionNotFoundException();

	}

	public void saveData() throws IOException {
		FileOutputStream stream = new FileOutputStream("sessions.ser");
		ObjectOutputStream objectStream = new ObjectOutputStream(stream);
		objectStream.writeObject(this.sessions);
		stream.close();
		objectStream.close();
	}

	public void readData() throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream("sessions.ser");
		ObjectInputStream objectStream = new ObjectInputStream(stream);
		Object obj = objectStream.readObject();
		if (obj instanceof ArrayList<?>)
			this.sessions = (ArrayList<Session>) obj;
		stream.close();
		objectStream.close();

	}

}
