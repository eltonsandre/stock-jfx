package com.github.eltonsandre.app.core.domain.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneRequired {

    String[] groups() default {};


    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = Enabled.AtLeastOneRequiredValidator.class)
    @interface Enabled {

        String[] fieldGroups() default {};

        String message() default "At least one of the specified fields ({fields}) is required from group '{group}'.";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};


        @Slf4j
        class AtLeastOneRequiredValidator implements ConstraintValidator<Enabled, Object> {

            private Enabled constraintAnnotation;

            public void initialize(Enabled constraintAnnotation) {
                this.constraintAnnotation = constraintAnnotation;
            }

            @Override
            public boolean isValid(Object object, ConstraintValidatorContext context) {
                if (object == null) {
                    return true;
                }

                List<Field> annotatedFields = getAnnotatedFields(object);
                boolean isValid = true;

                for (String group : constraintAnnotation.fieldGroups()) {
                    try {
                        var fields = annotatedFields.stream()
                                .map(field -> {
                                    AtLeastOneRequired annotation = field.getAnnotation(AtLeastOneRequired.class);
                                    if (annotation==null) {
                                        return null;
                                    }

                                    boolean contains = Arrays.asList(annotation.groups()).contains(group);

                                    return contains ? field : null;
                                })
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());

                        if (fields.isEmpty()) {
                            log.warn("The group '{}' does not contain attributes annotated with @AtLeastOneRequired(\"{}\").", group, group);
                            continue;
                        }

                        Set<String> empties = new LinkedHashSet<>();
                        for (Field field : fields) {
                            Object fieldValue = getFieldValue(object, field);
                            if (fieldValue != null && !fieldValue.toString().isBlank()) {
                                empties.clear();
                                break;
                            }

                            empties.add(field.getName());
                        }

                        if (empties.isEmpty()) {
                            continue;
                        }

                        isValid = false;
                        String fieldsJoin = StringUtils.join(empties, ", ");

                        context.unwrap(HibernateConstraintValidatorContext.class)
                                .addMessageParameter("group", group)
                                .addMessageParameter("fields", fieldsJoin)

                                .buildConstraintViolationWithTemplate(constraintAnnotation.message())
                                .addPropertyNode(fieldsJoin)
                                .addConstraintViolation()
                                .disableDefaultConstraintViolation();

                    } catch (Exception exception) {
                        log.error("AtLeastOneRequired error", exception);
                        isValid = false;
                    }
                }

                return isValid;
            }

            private List<Field> getAnnotatedFields(final Object object) {
                return Arrays.stream(object.getClass().getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(AtLeastOneRequired.class))
                        .collect(Collectors.toList());
            }

            private Object getFieldValue(final Object object, final Field field) throws IllegalAccessException {
                try {
                    field.setAccessible(true);
                    return field.get(object);
                } finally {
                    if (field != null) {
                        field.setAccessible(false);
                    }
                }
            }

        }

    }

}
