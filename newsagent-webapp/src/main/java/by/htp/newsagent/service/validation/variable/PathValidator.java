package by.htp.newsagent.service.validation.variable;

import java.util.regex.Pattern;

public final class PathValidator {
	private static final String PATH_VARIABLE_PATTERN = "\\d{1,}";

	public static boolean isPathVariableValid(String pathVariable) {
		if (pathVariable == null) {
			
			return false;
		}
		return Pattern.matches(PATH_VARIABLE_PATTERN, pathVariable);
	}
}
