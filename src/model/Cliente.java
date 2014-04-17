/**
Cliente
Class sets exceptions of Cliente.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Cliente.java.
 */

package model;

import exception.ClienteException;

public abstract class Cliente {

	// All attributes are String to make the validation process easier.

	// The CPF is a id number that every brazilian citizen have.
	private String cpf;
	private String name;
	private String phoneNumber;
	private String email;
	protected String enrollmentNumber;

	// Error messagens and alerts
	private final String INVALID_NAME = "Nome Invalido.";
	private final String BLANK_NAME = "Nome em Branco.";
	private final String NULL_NAME = "Nome esta Nulo.";
	private final String INVALID_CPF = "CPF Invalido.";
	private final String BLANK_CPF = "CPF em Branco.";
	private final String NULL_CPF = "CPF esta Nulo.";
	private final String INVALID_PHONE_NUMBER = "Telefone Invalido.";
	private final String NULL_PHONE_NUMBER = "Telefone esta Nulo.";
	private final String NULL_EMAIL = "E-mail esta Nulo.";

	public Cliente(String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException {

		this.setName(name);
		this.setCpf(cpf);
		this.setEnrollmentNumber(enrollmentNumber);
		this.setPhoneNumber(phoneNumber);
		this.setEmail(email);
	}

	public String getName() {

		return name;
	}

	public String getCpf() {

		return cpf;
	}

	public String getPhoneNumber() {

		return phoneNumber;
	}

	public String getEmail() {

		return email;
	}

	public String getEnrollmentNumber() {

		return enrollmentNumber;
	}

	public void setName(String name) throws ClienteException {

		if (name == null) {
			throw new ClienteException(NULL_NAME);
		} else {
			if ("".equals(name.trim())) {
				throw new ClienteException(BLANK_NAME);
			} else {
				if (name.trim().matches("[a-zA-Z][a-zA-Z\\s]+")) {
					this.name = name.trim();
				} else {
					throw new ClienteException(INVALID_NAME);
				}
			}
		}
	}

	public void setCpf(String cpf) throws ClienteException {

		if (cpf == null) {
			throw new ClienteException(NULL_CPF);
		} else {
			if ("".equals(cpf)) {
				throw new ClienteException(BLANK_CPF);
			} else {
				if (cpf.matches("[\\d]{3,3}.[\\d]{3,3}.[\\d]{3,3}-[\\d]{2,2}$")) {
					if (this.validateCpf(cpf.split("[\\. | -]")[0]
							+ cpf.split("[\\. | -]")[1]
							+ cpf.split("[\\. | -]")[2]
							+ cpf.split("[\\. | -]")[3])) {
						this.cpf = cpf;
					} else {
						throw new ClienteException(INVALID_CPF);
					}
				} else {
					throw new ClienteException(INVALID_CPF);
				}
			}
		}
	}

	public void setPhoneNumber(String phoneNumber) throws ClienteException {

		if (phoneNumber == null) {
			throw new ClienteException(NULL_PHONE_NUMBER);
		} else {
			if ("".equals(phoneNumber)) {
				this.phoneNumber = phoneNumber;
			// The phone number must be stored without spaces
			} else {
				if (phoneNumber
						.matches("(\\([ ]*[\\d]{2,3}[ ]*\\))?[ ]*[\\d]{4,4}[ ]*-?[ ]*[\\d]{4,4}[ ]*$")) {
					this.phoneNumber = phoneNumber.replaceAll(" ", "");
				} else {
					throw new ClienteException(INVALID_PHONE_NUMBER);
				}
			}
		}
	}

	public void setEmail(String email) throws ClienteException {

		if (email == null) {
			throw new ClienteException(NULL_EMAIL);
		} else {
			this.email = email;
		}
	}

	public abstract void setEnrollmentNumber(String enrollmentNumber)
			throws ClienteException;

	@Override
	public String toString() {

		return "Nome: " + name + "\nCpf: " + cpf + "\nTelefone: " + phoneNumber
				+ "\nEmail: " + email + "\nMatricula: " + enrollmentNumber;
	}

	public boolean equals(Cliente b) {

		if (this.getName().equals(b.getName())
				&& this.getCpf().equals(b.getCpf())
				&& this.getEnrollmentNumber().equals(b.getEnrollmentNumber())
				&& this.getPhoneNumber().equals(b.getPhoneNumber())
				&& this.getEmail().equals(b.getEmail())) {

			return true;
		} else {
			// Do nothing. 
		}
		return false;
	}

	private boolean validateCpf(String cpf) {

		int firstVerifierDigit, secondVerifierDigit;
		int firstDigit, secondDigit, leftover;
		int currentCpfDigit;
		String verifierDigitResult;

		firstVerifierDigit = secondVerifierDigit = 0;
		firstDigit = secondDigit = leftover = 0;

		for (int position = 1; position < cpf.length() - 1; position++) {
			currentCpfDigit = Integer.valueOf(
					cpf.substring(position - 1, position)).intValue();

			// Multiply the last number by 2, the following by 3, after by 4...
			firstVerifierDigit = firstVerifierDigit + (11 - position)
					* currentCpfDigit;

			// To the second verifier, the same thing, including the first one.
			secondVerifierDigit = secondVerifierDigit + (12 - position)
					* currentCpfDigit;
		}

		// We take the leftover to decide about the first digit value.
		leftover = (firstVerifierDigit % 11);

		if (leftover < 2) {
			firstDigit = 0;
		} else {
			firstDigit = 11 - leftover;
		}

		secondVerifierDigit += 2 * firstDigit;

		// The same thing, but to the second digit.
		leftover = (secondVerifierDigit % 11);

		if (leftover < 2) {
			secondDigit = 0;
		} else {
			secondDigit = 11 - leftover;
		}

		String finalVerifierDigit = cpf.substring(cpf.length() - 2, cpf.length());
		verifierDigitResult = String.valueOf(firstDigit) + String.valueOf(secondDigit);

		// If the expected is equals the calculated, the CPF is valid. 
		return finalVerifierDigit.equals(verifierDigitResult);

	}

}
