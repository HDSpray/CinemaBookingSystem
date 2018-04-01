import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * the CinemaBookingSystem class
 * @author Debao Jian (z5125716)
 *
 */
public class CinemaBookingSystem {
	public static void main(String[] args) {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(args[0]));  // args[0] is the first command line argument
			CinemaBookingSystem bookingSystem = new CinemaBookingSystem();
			while(sc.hasNext())
				bookingSystem.processArg(sc.nextLine());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sc != null) sc.close();
		}
	}


	/**
	 * Create the booking system
	 */
	public CinemaBookingSystem() {
		this.bookingList = new HashMap<Integer, Booking>();
		this.cinemaList = new HashMap<Integer, Cinema>();
	}

	/**
	 * add the cinema into the booking system
	 * @Pre quality > 0
	 * @Post the row will be added into the cinema if the cinema exist. if the cinema not exist, create the cinema
	 * @param cinema the name of the cinema
	 * @param rowName the row that the cinema have
	 * @param quality the number of the seat the row contain
	 */
	public void addCinema(int cinema, String rowName, int quality) {
		Cinema exitCinema = this.getCinema(cinema);
		if (exitCinema == null) {
			Cinema newCinema = new Cinema(cinema, rowName, quality);
			cinemaList.put(cinema, newCinema);
		} else {
			exitCinema.add(rowName, quality);
		}
			
	}

	/**
	 * get the cinema from the cinema
	 * @param cinema the name of the cinema
	 * @return return the cinema instance if the cinema exist, null otherwise
	 */
	public Cinema getCinema(int cinema) {
		return cinemaList.get(cinema);
	}

	/**
	 * get the booking from the bookingList
	 * @param id the id of the booking
	 * @return return the booking instance if the booking existm, null otherwise
	 */
	public Booking getBooking(int id) {
		return bookingList.get(id);
	}

	/**
	 * Request to book seats.
	 * @Pre the booking id is unique and is number, the cinema's name is number and it's exist, the time is valid and the tickets > 0
	 * @Post output "Booking rejected" if no enough seat, output new booking information. Changing the seat and store the booking if has enough seat
	 * @param id the booking id
	 * @param cinema the cinema's name
	 * @param time the session time
	 * @param tickets the number of the tickets
	 */
	public void request(int id, int cinema, String time, int tickets) {
		Cinema c = this.getCinema(cinema);
		Booking newBooking = c.request(id, time, tickets);
		if (newBooking == null) {
			System.out.println("Booking rejected");
		} else {
			bookingList.put(id, newBooking);
			System.out.println("Booking " + newBooking);
		}
	}

	/**
	 * Change the booking.
	 * @Pre tickets > 0, cinema and id exist and unique
	 * @Post change the booking information if change success, keep same if change rejected
	 * @param id the id of the booking that want to be change
	 * @param cinema the cinema that want the book
	 * @param time the new session time that want to book
	 * @param tickets the number of the ticket want to book
	 */
	public void change(int id, int cinema, String time, int tickets) {
		Booking origin = this.getBooking(id);
		if(origin == null) {
			// reject the booking if the booking not exist
			System.out.println("Change rejected");
			return;
		}
		// if the change within the same session and same cinema
		if (origin.isSameSession(time) && origin.isSameCinema(cinema)) {
			Cinema c = this.getCinema(cinema);
			if(c.change(id, time, tickets, origin)) {
				// change success, print the new booking information
				Booking changedBooking = this.getBooking(id);
				System.out.println("Change " + changedBooking);
			}else
				System.out.println("Change Rejected");
		} else {
			Cinema newCinema = this.getCinema(cinema);
			Cinema orginCinema = this.getCinema(origin.getCinema());
			orginCinema.cancel(origin.getTime(), id);                  // cancel the original booking
			Booking newBooking = newCinema.request(id, time, tickets); // required new booking
			if (newBooking == null) {
				// can not find available seat, then reset the origin booking
				orginCinema.resetBooking(origin);
				System.out.println("Change rejected");
			} else {
				// change success, update the bookingList
				bookingList.remove(id);
				bookingList.put(id, newBooking);
				System.out.println("Change " + newBooking);
			}
		}
	}

	/**
	 * Cancel the booking
	 * @param id the booking id
	 */
	public void cancel(int id) {
		Booking deleteBooking = bookingList.get(id);
		if (deleteBooking == null)
			System.out.println("Cancel rejected");
		else {
			Cinema c = this.getCinema(deleteBooking.getCinema());
			c.cancel(deleteBooking.getTime(), id);
			bookingList.remove(id);
			System.out.println("Cancel " + id);
		}
			
	}

	/**
	 * Print the booking information of the cinema's session
	 * @Pre the cinema must be exist, the time of the cinema must have session
	 * @param cinema the cinema name
	 * @param time the session time
	 */
	public void print(int cinema, String time) {
		Cinema c = this.getCinema(cinema);
		System.out.println(c.getSession(time));
	}

	/**
	 * Process the argument
	 * @param line the input line
	 */
	private void processArg(String line) {
		Scanner lineReader = new Scanner(line);
		String currCommand = "#";
		if (lineReader.hasNext()) 
			currCommand = lineReader.next();
		if (!currCommand.equals("#")) {
			switch (currCommand) {
				case "Cinema": 
					this.addCinema(lineReader.nextInt(), lineReader.next(), lineReader.nextInt());
					break;
				case "Session":
					Cinema c = this.getCinema(lineReader.nextInt());
					String time = lineReader.next();
					String movie = lineReader.next();
					while(lineReader.hasNext()) {
						String input = lineReader.next();
						if (input.equals("#")) break;
						movie = movie + " " + input;
					}
					c.addSession(time, movie); 
					break;
				case "Request":
					this.request(lineReader.nextInt(), lineReader.nextInt(), lineReader.next(), lineReader.nextInt());
					break;
				case "Change":
					this.change(lineReader.nextInt(), lineReader.nextInt(), lineReader.next(), lineReader.nextInt());
					break;
				case "Cancel":
					this.cancel(lineReader.nextInt());
					break;
				case "Print":
					this.print(lineReader.nextInt(), lineReader.next());
					break;
			}
		}
		lineReader.close();
	}
	
	private HashMap<Integer, Booking> bookingList;
	private HashMap<Integer, Cinema> cinemaList;
}
