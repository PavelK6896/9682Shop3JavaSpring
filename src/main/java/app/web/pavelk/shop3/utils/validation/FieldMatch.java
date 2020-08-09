package app.web.pavelk.shop3.utils.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldMatchValidator.class)//делегирует проверку класу
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})//область применения
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String message() default "";
    Class<?>[] groups() default {};//полезная нагрузка
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();
}