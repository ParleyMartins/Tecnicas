/**
Professor. 
Class sets exceptions of Professor.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Professor.java.
*/

package model;

import view.International;
import exception.ClienteException;

public class Teacher extends Client {

	// Alerts and error messages.
	private final String BLANK_ENROLLMENT_NUMBER = International.getInstance().getMessages()
					.getString("blankEnrollment");

	/**
	 * This method is for a professor enrollment. 
	 * @param name A professor name. 
	 * @param cpf A professor CPF number.
	 * @param enrollment_number A professor enrollment number. 
	 * @param phone_number A professor phone number. 
	 * @param email A professor electronic address. 
	 * @throws ClienteException It ensures that every parameter passed is not null. 
	 */
	public Teacher (String name, String cpf, String enrollment_number,
			String phone_number, String email) throws ClienteException {

		super(name, cpf, enrollment_number, phone_number, email);
	}

	/**This method sets an enrollment number. 
	 * @param enrollment_number A professor enrollment_number.
	 * @throws ClienteException It ensures that every parameter passed is not null. 
	 */
	public void setEnrollmentNumber(String enrollment_number) throws ClienteException {

		if (enrollment_number == null) {
			throw new ClienteException(BLANK_ENROLLMENT_NUMBER);
		} else {
			if ("".equals(enrollment_number.trim())) {
				throw new ClienteException(BLANK_ENROLLMENT_NUMBER);
			} else {
				// Do nothing; 
			}
		}
		super.enrollmentNumber = enrollment_number;
	}

}
