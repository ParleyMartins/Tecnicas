/**
Reserva. 
Class sets exceptions of Reserva.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Reserva.java.
*/

package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import view.International;
import exception.ReservaException;

public class Reserva {

	private String time;
	private String date;

	// Error Messages and alerts
	private final String INVALID_TIME = International.getInstance().getMessages()
					.getString("invalidTime");
	private final String BLANK_TIME = International.getInstance().getMessages()
					.getString("blankTime");
	private final String INVALID_DATE = International.getInstance().getMessages()
					.getString("invalidDate");
	private final String BLANK_DATE = International.getInstance().getMessages()
					.getString("blankDate");

	private final String TIME_PATTERN = "^[012]?[\\d]:[0-5][\\d]$";
	private final String DATE_PATTERN = "^[0123]?[\\d]([./-])[01]?[\\d]\\1[\\d]{2,4}$";

	/**
	 * This method is for a reservation. 
	 * @param date Reservation date. 
	 * @param time Reservation time. 
	 * @throws ReservaException It ensures that every parameter passed is valid. 
	 */
	public Reserva(String date, String time) throws ReservaException {

		this.setDate(date);
		this.setTime(time);
	}

	/**
	 * This method gets a time. 
	 * @return The content in the time field. 
	 */
	public String getTime() {

		return this.time;
	}

	/**
	 * This method gets a date.
	 * @return The content in the date field.
	 */
	public String getDate() {

		return this.date;
	}

	/**
	 * This method modifies the time field.
	 * @param time Reservation time.
	 * @throws ReservaException It ensures that every parameter passed is valid.
	 */
	public void setTime(String time) throws ReservaException {

		if (time == null) {
			throw new ReservaException(BLANK_TIME);
		} else {
			// Do nothing.
		}

		time = time.trim();
		if (time.equals("")) {
			throw new ReservaException(BLANK_TIME);
		} else {
			if (time.matches(TIME_PATTERN)) {
				if (time.length() == 4) {
					this.time = "0" + time;
				} else {
					this.time = time;
				}
			} else {
				throw new ReservaException(INVALID_TIME);
			}
		}
	}

	/**
	 * This method modifies the date field. 
	 * @param date Reservation date. 
	 * @throws ReservaException It ensures that every parameter passed is valid. 
	 */
	public void setDate(String date) throws ReservaException {

		if (date == null) {
			throw new ReservaException(BLANK_DATE);
		} else {
			// Do nothing. 
		}

		date = date.trim();
		if (date.equals("")) {
			throw new ReservaException(BLANK_DATE);
		} else {
			if (date.matches(DATE_PATTERN)) {
				this.date = standardizeDate(date);
			} else {
				throw new ReservaException(INVALID_DATE);
			}
		}
	}

	/**
	 * This method checks a reservation.
	 * @param reservation A reservation.
	 * @return whether there is a reservation.
	 */
	public boolean equals(Reserva reservation) {

		return (this.time.equals(reservation.getTime()) && this.date
				.equals(reservation.getDate()));
	}

	/** This method returns a String object representing the data.
	 * @return A reservation.
	 */
	public String toString() {

		return "\nHora=" + this.time + "\nData=" + this.date;
	}

	// Apply the date pattern to a String containing a date.
	/*
	 * Private Methods
	 */
	
	/**
	 * This method standardizes a date.
	 * @param date A reservation date.
	 * @return A standardized date.
	 */
	private static String standardizeDate(String date) {

		String currentDate[] = getCurrentDate().split("[./-]");
		String dateParts[] = date.split("[./-]");
		String formatedDate = "";

		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				formatedDate += currentDate[i].substring(0,
						currentDate[i].length() - dateParts[i].length())
						+ dateParts[i];
			} else {
				formatedDate += "/"
						+ currentDate[i].substring(0, currentDate[i].length()
								- dateParts[i].length()) + dateParts[i];
			}

		}

		return formatedDate;
	}

	// Returns the actual date formated, following the default pattern. 
	/*
	 * Private Methods
	 */
	
	/**
	 * This method gets the current date.
	 * @return The current date.
	 */
	private static String getCurrentDate() {

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		return formater.format(date);
	}
}
