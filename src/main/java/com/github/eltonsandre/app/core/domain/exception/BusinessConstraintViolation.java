package com.github.eltonsandre.app.core.domain.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessConstraintViolation extends Exception {

    private List<String> businessContraintsViolated = new ArrayList<>();

    public BusinessConstraintViolation(final String businessConstraint,
            final String message) {
        super(message);
        this.addBusinessContraintsViolated(businessConstraint);
    }

    public BusinessConstraintViolation(final List<String> businessConstraints,
            final String message) {
        super(message);
        this.setBusinessContraintsViolated(businessConstraints);
    }

    public BusinessConstraintViolation(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    public BusinessConstraintViolation(final String message) {
        super(message);
    }

    public List<String> getBusinessContraintsViolated() {
        return this.businessContraintsViolated;
    }

    public void setBusinessContraintsViolated(
            final List<String> businessContraintsViolated) {
        this.businessContraintsViolated = businessContraintsViolated;
    }

    public void addBusinessContraintsViolated(final String businessConstraint) {
        this.businessContraintsViolated.add(businessConstraint);
    }

}
