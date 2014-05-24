/**
PatrimonioException
Client Exceptions include constructors to create a exceptions for the erros related to client.
https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src/exception/PatrimonioException.java
*/

package exception;

@SuppressWarnings("serial")
public class PatrimonioException extends Exception {

	// Constructor creates a PatrimonioExcpetion without any argument.
	public PatrimonioException() {

		super();
	}

	// Constructor creates a PatrimonioExcpetion with a message.
	public PatrimonioException(String message) {

		super(message);
	}

}
