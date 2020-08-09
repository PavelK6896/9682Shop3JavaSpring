package app.web.pavelk.shop3.utils.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override //грузим данные из аннотации
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override//пердикат
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try {//рефлексивно дастоет значение полей из объекта
            final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
            //поле не равно нул и равны между сабой
            valid = firstObj != null && firstObj.equals(secondObj);
        } catch (final Exception ignore) {
        }
        if (!valid) {//не валиден

            //месадж из аннотации прикрепляеться к полям в для биндинг резалта
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}