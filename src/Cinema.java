import java.util.ArrayList;
import java.util.HashMap;
/**
 * the Cinema class
 * @author Debao Jian (z5125716)
 */
public class Cinema {

	/**
	 * Create the cinema.
	 * @Pre seats > 0 
	 * @Post create the cinema instace
	 * @param name the name of the cinema
	 * @param rowName the name of the row
	 * @param seats the available seat of this row
	 */
	public Cinema(int name, String rowName, int seats) {
		this.name = name;
		this.session = new HashMap<String, Session>();
		this.rowList = new ArrayList<Row>();
		this.add(rowName, seats);
	}

	/**
	 * Add a new row to this cinema
	 * @Pre the row is not in this cinema, seats > 0 
	 * @Post the row will be added into the cinema's row list
	 * @param rowName the name of the row
	 * @param seats the number of the seat of this row
	 */
	public void add(String rowName, int seats) {
		Row newSeat = new Row(seats, rowName);
		this.rowList.add(newSeat);
	}

	/**
	 * Add a new session into the cinema
	 * @Pre the time must be valid format
	 * @Post create a session and add into the session list
	 * @param time the time of the session
	 * @param movie the movie name
	 */
	public void addSession(String time, String movie) {
		ArrayList<Row> sessionRow = new ArrayList<Row>();
		for(Row r:rowList) 
			sessionRow.add(r.rowBookingSystem());
		Session newSession = new Session(time, movie, sessionRow);
		this.session.put(time, newSession);
	}

	/**
	 * get the session
	 * @Pre the time must be valid, and exist session with this time
	 * @Post return the session object
	 * @param time the time of the session
	 * @return return the session with this time
	 */
	public Session getSession(String time) {
		return this.session.get(time);
	}

	/**
	 * Request to book seats.
	 * @Pre the booking id is unique and is number, the time is valid and the tickets > 0
	 * @Post if there is enough seats changing the seat and return the booking instance if booking success, return null otherwise.
	 * @param id the booking id
	 * @param time the session time
	 * @param tickets the number of tickets
	 * @return return the booking instance if booking success, null otherwise.
	 */
	public Booking request(int id, String time, int tickets) {
		Session s = session.get(time);
		return s.request(id, this.name, tickets);
	}

	/**
	 * Change the booking.
	 * @Pre the booking id must exist, tickets > 0, time valid and exist corresponding session
	 * @Post change the booking information if the changing success, nothing change if the change fail
	 * @param id the booking that want to be change
	 * @param time the new booking time
	 * @param tickets the number of the tickets that want to be book
	 * @param origin the origin booking information
	 * @return true if the change success, false otherwise
	 */
	public Boolean change(int id, String time, int tickets, Booking origin) {
		Session s = session.get(time);
		return s.change(id, this.name, tickets, origin);
	}
	

	/**
	 * reset the booking information
	 * @Pre the origin booking must be exist
	 * @Post reset the origin booking within the system
	 * @param origin the origin booking
	 */
	public void resetBooking(Booking origin) {
		Session s = this.getSession(origin.getTime());
		s.resetBooking(origin);
	}
	
	/**
	 * Cancel the booking
	 * @Pre the time in valid format and have exist session, booking with this id must be exist
	 * @Post cancel the booking and reset the seat
	 * @param time the booking time
	 * @param id the id of the booking
	 */
	public void cancel(String time, int id) {
		Session s = this.session.get(time);
		s.cancelBooking(id);
	}
	
	private int name;
	private HashMap<String, Session> session;
	private ArrayList<Row> rowList;
}
