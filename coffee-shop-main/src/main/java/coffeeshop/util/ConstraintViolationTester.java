package coffeeshop.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConstraintViolationTester<T> {

    private static final Logger LOG = Logger.getLogger(ConstraintViolationTester.class.getName());

    public boolean validateAndLog(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<T> cv : constraintViolations) {
                LOG.log(Level.SEVERE, "{0}.{1} {2}", new Object[]{
                        cv.getRootBeanClass().getName(),
                        cv.getPropertyPath(),
                        cv.getMessage()
                });
            }
            return false;
        } else {
            return true;
        }
    }
}
