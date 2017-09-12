package by.htp.newsagent.service.exception;

public class NewsValidationException extends IllegalArgumentException {
	private static final long serialVersionUID = 6287787227547826449L;

	public NewsValidationException() {

	}

	public NewsValidationException(String message, Throwable exception) {
		super(message, exception);
	}

	public NewsValidationException(String message) {
		super(message);
	}

	public NewsValidationException(Throwable exception) {
		super(exception);
	}
}
