package by.htp.newsagent.service.validation.date;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
public @interface DateRange {
	String message() default "by.htp.newsagent.DateRange.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String min() default "2017-01-01";

	String max() default "2100-12-31";
}
