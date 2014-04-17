/**
Aluno
Class sets exceptions of Aluno.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Aluno.java.
*/

package model;

import exception.ClienteException;

public class Aluno extends Cliente {

	// Error messages and alerts.
	private final String BLANK_ENROLLMENT_NUMBER = "Matricula em Branco.";
	private final String NULL_ENROLLMENT_NUMBER = "Matricula esta Nula.";

	public Aluno(String name, String cpf, String enrollment_number,
			String phone_number, String email) throws ClienteException {

		super(name, cpf, enrollment_number, phone_number, email);
	}

	public void setEnrollmentNumber(String enrollment_number)
			throws ClienteException {

		if (enrollment_number == null) {
			throw new ClienteException(NULL_ENROLLMENT_NUMBER);
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
