package de.schulung.quarkus.recipes.shared.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({
  ElementType.METHOD,
  ElementType.FIELD,
  ElementType.PARAMETER,
  ElementType.ANNOTATION_TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.TYPE_USE
})
@Documented
@Constraint(validatedBy = {})
@Size(max = 255)
@Pattern(regexp = "^(/|https?://).+")
@ReportAsSingleViolation
public @interface ValidUrl {

  String message() default "must be a valid URL (absolute or starting with /) with at most 255 characters";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
