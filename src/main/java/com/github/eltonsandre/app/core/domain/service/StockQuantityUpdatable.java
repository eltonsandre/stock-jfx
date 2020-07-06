package com.github.eltonsandre.app.core.domain.service;

import com.github.eltonsandre.app.core.domain.exception.BusinessConstraintViolation;
import com.github.eltonsandre.app.core.domain.exception.NonExistentEntityException;
import com.github.eltonsandre.app.core.domain.model.enuns.OperationTypeEnum;

public interface StockQuantityUpdatable {

    void updateStockQuantity(final Long id, final Integer quantity, final OperationTypeEnum operationTypeEnum)
            throws NonExistentEntityException, BusinessConstraintViolation;

}
