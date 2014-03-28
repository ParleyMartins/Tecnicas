/**
  ClienteException
  https://github.com/ParleyMartins/Tecnicas/blob/estiloDesign/src
  /exception/ClienteException.java
 */
package exception;

@SuppressWarnings ("serial")
public class ClienteException extends Exception {

	// Constructor creates a ClientExcpetion without any argument.
	public ClienteException ( ) {

		super( );
	}

	// Constructor creates a ClientExcpetion with a message.
	public ClienteException (String message) {

		super(message);
	}

}
