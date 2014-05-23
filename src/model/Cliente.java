/**
Cliente
Class sets exceptions of Cliente.
https://github.com/ParleyMartins/Tecnicas/tree/master/src/model/Cliente.java.
*/

package model;

import view.International;
import exception.ClienteException;

public abstract class Cliente {

	// All attributes are String to make the validation process easier.

	// The CPF is a id number that every Brazilian citizen have.
	private String cpf;
	private String name;
	private String phoneNumber;
	private String email;
	protected String enrollmentNumber;

	// Error messages and alerts
	private final String INVALID_NAME = International.getInstance().getMessages()
					.getString("invalidName");
	private final String BLANK_NAME = International.getInstance().getMessages()
					.getString("blankName");
	private final String INVALID_CPF = International.getInstance().getMessages()
					.getString("invalidCPF");
	private final String BLANK_CPF = International.getInstance().getMessages()
					.getString("blankCPF");
	private final String INVALID_PHONE_NUMBER = International.getInstance().getMessages()
					.getString("invalidPhone");
	private final String BLANK_PHONE_NUMBER = International.getInstance().getMessages()
					.getString("blankPhone");
	private final String BLANK_EMAIL = International.getInstance().getMessages()
					.getString("blankEmail");

	/**
	 * This method sets informations related to a client. 
	 * @param name Field for a person name. 
	 * @param cpf Field for a CPF number. 
	 * @param enrollmentNumber Field for a enrollment number. 
	 * @param phoneNumber Field for a phone number.
	 * @param email Field for an electronic address (e-mail). 
	 * @throws ClienteException It ensures that every parameter passed is not null.
	 */
	public Cliente(String name, String cpf, String enrollmentNumber,
			String phoneNumber, String email) throws ClienteException {

		this.setName(name);
		this.setCpf(cpf);
		this.setEnrollmentNumber(enrollmentNumber);
		this.setPhoneNumber(phoneNumber);
		this.setEmail(email);
	}
	
	/**
	 * This method gets a name.
	 * @return The content in the name field.
	 */
	public String getName() {

		return name;
	}

	/**
	 * This method gets a cpf. 
	 * @return The content in the cpf field.
	 */
	public String getCpf() {

		return cpf;
	}
	
	/**
	 * This method gets a phone number.
	 * @return The content in the phoneNumber field.
	 */
	public String getPhoneNumber() {

		return phoneNumber;
	}

	/**
	 * This method gets an email. 
	 * @return The content in the email field.
	 */
	public String getEmail() {

		return email;
	}

	/** This method gets an enrollment number.
	 * @return The content in the enrollmentNumber field.
	 */
	public String getEnrollmentNumber() {

		return enrollmentNumber;
	}

	/**
	 * This method modifies the name field. 
	 * @param name A client name. 
	 * @throws ClienteException It ensures that every parameter passed is valid. 
	 */
	public void setName(String name) throws ClienteException {

		if (name == null) {
			throw new ClienteException(BLANK_NAME);
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

	/**
	 * This method modifies the cpf field.
	 * @param cpf A client CPF number. 
	 * @throws ClienteException It ensures that every parameter passed is valid. 
	 */
	public void setCpf(String cpf) throws ClienteException {

		if (cpf == null) {
			throw new ClienteException(BLANK_CPF);
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

	/**
	 * This method modifies the phone number field to a valid phone number. 
	 * @param phoneNumber A client phone number.
	 * @throws ClienteException It ensures that every parameter passed is valid.
	 */
	public void setPhoneNumber(String phoneNumber) throws ClienteException {

		if (phoneNumber == null) {
			throw new ClienteException(BLANK_PHONE_NUMBER);
		} else {
			if ("".equals(phoneNumber)) {
				this.phoneNumber = phoneNumber;
			// The phone number must be stored without spaces
			} else {
				if (phoneNumber
						.matches("(\\([ ]*[\\d]{2,3}[ ]*\\))?[ ]*[\\d]{4,4}"
									+ "[ ]*-?[ ]*[\\d]{4,4}[ ]*$")) {
					this.phoneNumber = phoneNumber.replaceAll(" ", "");
				} else {
					throw new ClienteException(INVALID_PHONE_NUMBER);
				}
			}
		}
	}

	/**
	 * This method modifies the email field.
	 * @param A client electronic address.
	 * @throws ClienteException It ensures that every parameter passed is valid. 
	 */
	public void setEmail(String email) throws ClienteException {

		if (email == null) {
			throw new ClienteException(BLANK_EMAIL);
		} else {
			this.email = email;
		}
	}
	/**
	 * This method modifies the enrollmentNumber field.
	 * @param enrollmentNumber A client enrollment number.
	 * @throws ClienteException It ensures that every parameter passed is valid. 
	 */
	public abstract void setEnrollmentNumber(String enrollmentNumber)
			throws ClienteException;

	/**
	 * This method is used to get a client information returning a String.
	 * Object representing the data. 
	 * @return All register fields. 
	 */
	public String toString() {

		return "Nome: " + name + "\nCpf: " + cpf + "\nTelefone: " + phoneNumber
				+ "\nEmail: " + email + "\nMatricula: " + enrollmentNumber;
	}
	/**
	 * This method checks if a client is registered.
	 * @param cliente
	 * @return true if a client has a register. false otherwise.
	 */
	public boolean equals(Cliente cliente) {

		if (this.getName().equals(cliente.getName())
				&& this.getCpf().equals(cliente.getCpf())
				&& this.getEnrollmentNumber().equals(cliente.getEnrollmentNumber())
				&& this.getPhoneNumber().equals(cliente.getPhoneNumber())
				&& this.getEmail().equals(cliente.getEmail())) {

			return true;
		} else {
			// Do nothing. 
		}
		return false;
	}
	
	/*
	 * Private Methods
	 */
	
	/**
	 * This method validates an inserted CPF.
	 * @param cpf Registration of an individual. 
	 * @return true if it is valid. false otherwsise. 
	 */
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
