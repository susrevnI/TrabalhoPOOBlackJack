package program.exceptions;

public class BaralhoVazioException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BaralhoVazioException() {
		super();
	}
	
	public BaralhoVazioException(String message) {
		super(message);
	}
}
