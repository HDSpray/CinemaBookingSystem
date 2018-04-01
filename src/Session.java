import java.util.ArrayList;

/**
 * the Session class 
 * @author Debao Jian (z5125716)
 */
public class Session {
	/**
	 * Create a session
	 * @param time the session time 
	 * @param movie the movie of this session
	 * @param row the row that available for book
	 */
	public Session(String time, String movie, ArrayList<Row> row) {
		this.time = time;
		this.movie = movie;
		this.rowList = row;
		this.bookingList = new ArrayList<Booking>();
	}

	/**
	 * Add booking information of this session
	 * @Pre the newBooking exist and belong to this session
	 * @Post newBooking belongs to bookingList
//	 * @param newBooking the new booking of this session
	 */
	public void addBooking(Booking newBooking) {
		bookingList.add(newBooking);
	}

	/**
	 * Reset the booking information
	 * @Pre the booking must be exist already
	 * @Pst reset the origin booking into the system
	 * @param booking the origin booking that wants to reset
	 */
	public void resetBooking(Booking booking) {
		bookingList.add(booking);
		String rowName = booking.getRow();
		for (Row r:rowList) {
			if (r.getName() == rowName) 
				r.resetSeat(booking.getStartSeat(), booking.getEndSeat());
		}
	}
	
	/**
	 * Request to book seats.
	 * @Pre the booking id is unique and is number, the tickets > 0 and the cinema is exist, the current session time is valid 
	 * @Post if having enough seats, it creates the new booking, change the seat state and return new booking. Otherwise return null
	 * @param id the booking id
	 * @param cinema the cinema's name
	 * @param tickets the number of the tickets
	 * @return the new booking instance if having enough seats, null otherwise.
	 */
	public Booking request(int id, int cinema, int tickets) {
		int startSeat = -1;
		Booking newBooking = null;
		for(Row r:rowList) {
			startSeat = r.requirdSeat(tickets);
			if(startSeat != -1) {
				newBooking = new Booking(id, cinema, this.time, r.getName(), startSeat, tickets);
				bookingList.add(newBooking);
				break;
			}
		}
		return newBooking;
	}

	/**
	 * Change the booking 
	 * @Pre the origin booking must exist, tickets > 0, cinema and id exist and unique
	 * @Post change the booking information if change success, keep same if change rejected
	 * @param id the booking that want to change
	 * @param cinema the cinema that want the book
	 * @param tickets the number of the ticket want to book
	 * @param origin the origin booking 
	 * @return true if the change success, false otherwise
	 */
	public Boolean change(int id, int cinema, int tickets, Booking origin) {
		// find the origin booking row
		String rowName = origin.getRow();
		Row originRow = null;
		for(Row r:rowList) {
			if (r.getName() == rowName) {
				originRow = r;
				break;
			}
		}
		// find the available seat
		int startSeat = -1;
		originRow.cancelBooking(origin.getStartSeat());
		originRow.connect();
		for(Row r:rowList) {
			startSeat = r.requirdSeat(tickets);
			if(startSeat != -1) {
				origin.setRow(r.getName());
				origin.setStartSeat(startSeat);
				origin.setEndSeat(startSeat + tickets -1);
				break;
			}
		}
		// if can not find the available seat, reset to the origin booking
		if(startSeat == -1) originRow.resetSeat(origin.getStartSeat(), origin.getEndSeat());
		return (startSeat != -1);
	}
	

	/**
	 * Cancel the booking
	 * @Pre must exist unique booking with the input id, stored in this session's bookingList
	 * @Post the booking with the input id will remove from the bookingList and set the seat to be available
	 * @param id the booking id that wants to be cancel
	 */
	public void cancelBooking(int id) {
		for (int i = 0; i < bookingList.size(); i++) {
			Booking b = bookingList.get(i);
			if (b.getId() == id) {
				String rowName = b.getRow();
				for (Row r:rowList) 
					if (r.getName() == rowName)
						r.cancelBooking(b.getStartSeat());
				bookingList.remove(i);
				break;
			}
		}
	}
	

	/**
	 * Translate the session's information into string representation
	 * @return return the string representation about the session's information
	 */
	public String toString() {
		String returnString = this.movie;
		for(int i = 0; i < rowList.size(); i++) {
			Row r = rowList.get(i);
			String rowInfo = r.toString();
			if(rowInfo.isEmpty()) continue;
			returnString += "\n";
			returnString += r.toString();
		}
		return returnString;
	}
	
	private ArrayList<Row> rowList;
	private ArrayList<Booking> bookingList;
	private String time;
	private String movie;
}
