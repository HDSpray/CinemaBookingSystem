/**
 * the class represent the row of the seats
 * @author Debao Jian (z5125716)
 * @invariant startSeat > 0, endSeat >0, startSeat < endSeat
 */
public class Seat {

	/**
	 * Create a row of seat.
	 * @Pre startSeat > 0, totalSeat > 0
	 * @Post this.startSeat = startSeat, this.endSeat = this.startSeat + totalSeat - 1
	 * @param startSeat the startSeat of this row of seat
	 * @param totalSeat the total number of the seat of this row of seat
	 */
	public Seat(int startSeat, int totalSeat) {
		this.startSeat = startSeat;
		this.endSeat = this.startSeat + totalSeat - 1;
	}

	/**
	 * Get the value of the startSeatd.
	 * @return return the value of the startSeat
	 */
	public int getStartSeat() {
		return startSeat;
	}

	/**
	 * Get the value of the endSeat.
	 * @return return the value of the endSeat
	 */
	public int getEndSeat() {
		return endSeat;
	}
	
	/**
	 * Set this.endSeat to the endSeat.
	 * @Pre endSeat > startSeat, endSeat > 0
	 * @Post this.endSeat = endSeat;
	 * @param endSeat the endSeat to be set
	 */
	public void setEndSeat(int endSeat) {
		this.endSeat = endSeat;
	}

	/**
	 * the length of this row of the seat.
	 * @return return the length of this row of the seat
	 */
	public int size() {
		return endSeat - startSeat + 1;
	}

	/**
	 * Check whether have enough seat for booking
	 * @Pre requiredNumber > 0
	 * @Post endSeat-startSeat+1>=requiredNumber?
	 * @param requiredNumber the number of seats that wants to book
	 * @return true if there is enough seats, false otherwise
	 */
	public boolean avaiable(int requiredNumber) {
		return (endSeat - startSeat + 1 >= requiredNumber);
	}

	/**
	 * change the seat state to remain seat
	 * @Pre requirdNumber > 0
	 * @Post this.startSeat = this.startSeat + reuqirdNumber
	 * @param requirdNumber the number of the seat that are required to book
	 */
	public void bookSeat(int requiredNumber) {
		this.startSeat = this.startSeat + requiredNumber;
	}
	
	/**
	 * toString method, translate the Seat information into the string
	 * @return return the String object represent the seat information
	 */
	public String toString() {
		if(startSeat == endSeat) return Integer.toString(startSeat);
		return startSeat + "-" + endSeat;
	}
	
	private int startSeat;
	private int endSeat;
}
