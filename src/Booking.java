/**
 * The class that represent the booking 
 * @author Debao Jian (z5125716)
 */
public class Booking {
	
	/**
	 * Constructor, constructor a booking instance
	 * @param id the booking id
	 * @param cinema the cinema name
	 * @param time the session time
	 * @param rowName the row that have been booked
	 * @param startSeat the startSeat of the booked row
	 * @param tickets the number of the tickets that have been booked
	 */
	public Booking(int id, int cinema, String time, String rowName, int startSeat, int tickets) {
		this.id = id;
		this.cinema = cinema;
		this.time = time;
		this.rowName = rowName;
		this.startSeat = startSeat;
		this.endSeat = this.startSeat + tickets -1;
	}
	
	/**
	 * Get the booking id.
	 * @return return the booking id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Get the cinema's name.
	 * @return return the cinema's name
	 */
	public int getCinema() {
		return cinema;
	}
	
	/**
	 * Get the session time.
	 * @return return the session time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Get the name of the booked row.
	 * @return return the name of the name of the booked row
	 */
	public String getRow() {
		return rowName;
	}

	/**
	 * Get the startSeat.
	 * @return return the startSeat of the row
	 */
	public int getStartSeat() {
		return startSeat;
	}

	/**
	 * Get the endSeat.
	 * @return return the endSeat of the row
	 */
	public int getEndSeat() {
		return endSeat;
	}

	/**
	 * Set the row name.
	 * @Pre the row name is belong to the cinema
	 * @Post this.rowName = row
	 * @param row the row to set
	 */
	public void setRow(String row) {
		this.rowName = row;
	}

	/**
	 * Set the startSeat.
	 * @Pre endSeat >= startSeat
	 * @Post this.startSeat = startSeat
	 * @param startSeat the startSeat to set
	 */
	public void setStartSeat(int startSeat) {
		this.startSeat = startSeat;
	}
	
	/**
	 * Set the endSeat.
	 * @Pre endSeat >= startSeat
	 * @Post this.endSeat = endSeat
	 * @param endSeat the endSet to set
	 */
	public void setEndSeat(int endSeat) {
		this.endSeat  = endSeat;
	}

	/**
	 * Check whether the input time same as the booking session time.
	 * @Pre True
	 * @Post this.time == time?
	 * @param time the session time
	 * @return true if the booking session time is same as the input session time, false otherwise
	 */
	public boolean isSameSession(String time) {
		return this.time.equals(time);
	}
	
	/**
	 * Check whether the input cinema same as the booking cinema
	 * @Pre cinema name is unique
	 * @Post this.cinema == cinema?
	 * @param cinema the cinema's name
	 * @return true if the cinema same as the booking cinema, false otherwise
	 */
	public boolean isSameCinema(int cinema) {
		return (this.cinema == cinema);
	}
	
	
	/**
	 * toString method, translate the booking information to string
	 */
	public String toString() {
		if(startSeat == endSeat) return (this.id + " " + this.rowName + this.startSeat);
		return (this.id + " " + this.rowName
				+ this.startSeat + "-" + this.rowName + this.endSeat);
	}
	

	private int id;
	private int cinema;
	private String time;
	private String rowName;
	private int startSeat;
	private int endSeat;
}
