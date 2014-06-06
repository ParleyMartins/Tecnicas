/**
Aluno
Class sets exceptions of Aluno.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Aluno.java.
*/

package model;

import view.International;
import exception.ClienteException;

public class Student extends Cliente {

	// Error messages and alerts.
	private final String BLANK_ENROLLMENT_NUMBER = International.getInstance().getMessages()
					.getString("blankEnrollment");

	/**
	 * Basic attributes of registration.
	 * @param name is how the student is called.
	 * @param cpf is the code that every citizen has.
	 * @param enrollment_number is the number of enrolled student.
	 * @param phone_number is the phone number of the student.
	 * @param email is the electronic address of the student.
	 * @throws ClienteExceptionprevention error when calling the method.
	 */
	public Student(String name, String cpf, String enrollment_number,
			String phone_number, String email) throws ClienteException {

		super(name, cpf, enrollment_number, phone_number, email);
	}

	/** set the registration.
	 * @param enrrollment_number is the number of enrolled student.
	 * @throws ClienteException prevention error when calling the method.
	 */
	public void setEnrollmentNumber(String enrollment_number)
			throws ClienteException {

		if (enrollment_number == null) {
			throw new ClienteException(BLANK_ENROLLMENT_NUMBER);
		} else {
			if ("".equals(enrollment_number.trim())) {
				throw new ClienteException(BLANK_ENROLLMENT_NUMBER);
			} else {
				// Do nothing.
			}
		}
		super.enrollmentNumber = enrollment_number;
	}
}
