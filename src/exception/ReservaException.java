/**
ReservaException
Exception type to Reserva's errors
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/exception/ReservaException.java
*/

package exception;

@SuppressWarnings("serial")
public class ReservaException extends Exception {

	// Constructor creates a ReservaExcpetion without any argument
	public ReservaException() {

		super();
	}

	// Constructor creates a ReservaExcpetion with a message.
	public ReservaException(String message) {

		super(message);
	}
}
