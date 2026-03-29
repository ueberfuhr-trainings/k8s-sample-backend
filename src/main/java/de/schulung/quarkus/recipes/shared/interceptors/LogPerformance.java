package de.schulung.quarkus.recipes.shared.interceptors;

import jakarta.interceptor.InterceptorBinding;
import org.jboss.logging.Logger;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({
  ElementType.METHOD,
  ElementType.TYPE
})
@Documented
@InterceptorBinding
public @interface LogPerformance {

  Logger.Level value() default Logger.Level.INFO;

}
