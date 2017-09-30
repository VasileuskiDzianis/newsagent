package by.htp.newsagent.validator.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, java.util.Date> {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private DateRange constraintAnnotation;

	@Override
	public void initialize(DateRange constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		try {
			final Date min = DATE_FORMAT.parse(constraintAnnotation.min());
			final Date max = DATE_FORMAT.parse(constraintAnnotation.max());

			return value == null || !(value.before(min) || value.after(max));
		} catch (ParseException ex) {
			throw new RuntimeException("Illegal format of date constraints", ex);
		}
	}
}
