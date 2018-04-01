import java.util.*;
/**
 * The row class
 * @author Debao Jian (z5125716)
 *
 */
public class Row {
	/**
	 * Create the row 
	 * @Pre totalSeat > 0
	 * @Post create the row object
	 * @param totalSeat the total number of the seat the row have
	 * @param name the name of the row
	 */
	public Row(int totalSeat, String name) {
		this.name = name;
		this.availableSeat = new ArrayList<Seat>();
		this.bookedSeat= new ArrayList<Seat>();
		Seat newSeat = new Seat(DEFAUT_START_SEAT, totalSeat);
		availableSeat.add(newSeat);
	}

	/**
	 * get the name of the row
	 * @return return the name of the row
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Request to book seats.
	 * @Pre the availableSeat are sorted by the startSeat 
	 * @Post availablSeat are sorted by the startSeat, change the booked seat into the bookedSeat
	 * @param quality the number of tickets that are required
	 * @return the startSeat index if having enough seats, otherwise return -1 indicate do not have enough seats
	 */
	public int requirdSeat(int quality) {
		int startSeat = -1;
		for(int i = 0; i < availableSeat.size(); i++) {
			Seat s = availableSeat.get(i);
			if(s.avaiable(quality)) {
				// have enough seat
				startSeat = s.getStartSeat();
				int newStartSeat = s.getStartSeat();
				Seat booked = new Seat(newStartSeat, quality);
				if(s.size() != quality)
					s.bookSeat(quality);
				else
					availableSeat.remove(i);
				bookedSeat.add(booked);
				this.sortSeat();
				this.connect();
				break;
			}
		}
		return startSeat;
	}

	/**
	 * reset the seat
	 * @Pre 0 < startSeat <= endSeat, the range of the seat from startSeat to endSeat must be available
	 * @Post set the range of the seat form startSeat to endSeat to booked
	 * @param startSeat the startSeat index
	 * @param endSeat the endSeat index
	 */
	public void resetSeat(int startSeat, int endSeat) {
		for(int i = 0; i < availableSeat.size(); i++) {
			Seat s = availableSeat.get(i);
			if(s.getStartSeat() == startSeat) {
				int size = endSeat - startSeat + 1;
				Seat booked = new Seat(startSeat, size);
				if(s.size() != size)
					s.bookSeat(size);
				else
					availableSeat.remove(i);
				bookedSeat.add(booked);
				this.sortSeat();
				this.connect();
				break;
			}else if(s.getStartSeat() < startSeat  && startSeat <= s.getEndSeat()) {
				Seat booked = new Seat(startSeat, endSeat-startSeat+1);
				bookedSeat.add(booked);
				if (endSeat != s.getEndSeat()) {
					Seat remain = new Seat(endSeat+1, s.getEndSeat());
					availableSeat.add(remain);
				}
				s.setEndSeat(startSeat-1);
				this.sortSeat();
				this.connect();
				break;
			}
		}
	}

	/**
	 * Cancel the booking.
	 * @Pre there must be exist booking with this startSeat in bookedSeat
	 * @Post the seats with this startSeat will be changed to available
	 * @param startSeat the startSeat of the booking
	 */
	public void cancelBooking(int startSeat) {
		int i = 0;
		for (i = 0; i < bookedSeat.size(); i++) {
			Seat s = bookedSeat.get(i);
			if (s.getStartSeat() == startSeat) {
				availableSeat.add(s);
				this.sortSeat();
				this.connect();
				break;
			}
		}
		bookedSeat.remove(i);
	}

	/**
	 * translate to the string representation
	 * @return return the string representation of this row
	 */
	public String toString() {
		this.sortBookedSeat();
		if (bookedSeat.size() == 0) return "";
		String returnString = name + ": ";
		for (int i = 0; i < bookedSeat.size(); i++) {
			Seat s = bookedSeat.get(i);
			returnString += s.toString();
			if(i != bookedSeat.size()-1) returnString += ",";
		}
		return returnString;
	}

	/**
	 * Create the row of manager
	 * @return return a new row to manage this row
	 */
	public Row rowBookingSystem() {
		Row row = new Row(availableSeat.get(INDEX_OF_AVAILABLE_SEAT).size(), name);
		return row;
	}


	/**
	 * Sort the availablseSeat with the startSeat.
	 */
	private void sortSeat() {
		Collections.sort(availableSeat, Comparator.comparing(Seat::getStartSeat));
	}
	
	/**
	 * Sort the bookedSeat with the startSeat.
	 */
	private void sortBookedSeat() {
		Collections.sort(bookedSeat, Comparator.comparing(Seat::getStartSeat));
	}

	/**
	 * Connect all the availableSeat if there endSeat + 1 equal to next startSeat
	 * @Pre the this.availableSeat have already sorted
	 * @Post all the Seat which endSeat + 1 equal to next StartSeat will connect
	 */
	public void connect() {
		for (int i = 0; i < availableSeat.size()-1; i++) {
			Seat currSeat = availableSeat.get(i);
			Seat nextSeat = availableSeat.get(i+1);
			if (currSeat.getEndSeat() + 1 == nextSeat.getStartSeat()) {
				currSeat.setEndSeat(nextSeat.getEndSeat());
				availableSeat.remove(i+1);
			}
		}
	}
	
	private String name;
	private ArrayList<Seat> availableSeat;
	private ArrayList<Seat> bookedSeat;
	private static final int DEFAUT_START_SEAT = 1;
	private static final int INDEX_OF_AVAILABLE_SEAT = 0;
}
