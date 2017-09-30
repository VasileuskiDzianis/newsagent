package by.htp.newsagent.validator.path;

import java.util.regex.Pattern;

public final class PathValidator {
	private static final String PATH_VARIABLE_PATTERN = "\\d{1,}";
	
	private PathValidator() {
		
	}

	public static boolean isPathVariableValid(String pathVariable) {
		if (pathVariable == null) {
			
			return false;
		}
		return Pattern.matches(PATH_VARIABLE_PATTERN, pathVariable);
	}
}
