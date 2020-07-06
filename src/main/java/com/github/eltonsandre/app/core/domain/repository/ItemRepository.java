package com.github.eltonsandre.app.core.domain.repository;

import com.github.eltonsandre.app.core.domain.exception.BusinessConstraintViolation;
import com.github.eltonsandre.app.core.domain.exception.NonExistentEntityException;
import com.github.eltonsandre.app.core.domain.model.entity.Item;
import com.github.eltonsandre.app.core.domain.model.enuns.OperationTypeEnum;
import com.github.eltonsandre.app.core.domain.service.StockQuantityUpdatable;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author eltonsandre
 * date 09/05/2020 18:14
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, StockQuantityUpdatable {

    @Query("SELECT item FROM Item item WHERE item.createdAt = :createdAt")
    List<Item> findByCreatedAt(@Param("createdAt") LocalDateTime createdAt);

    @Query("SELECT item FROM Item item WHERE item.name = :itemName")
    List<Item> findByName(@Param("itemName") String itemName);

    @Query("SELECT item FROM Item item WHERE item.stockQuantity = :stockQuantity")
    List<Item> findByStockQuantity(@Param("stockQuantity") int stockQuantity);

    @Query("SELECT item FROM Item item WHERE item.lastStockUpdate = :lastStockUpdate")
    List<Item> findByLastStockUpdate(@Param("lastStockUpdate") LocalDateTime lastStockUpdate);

    @Query("SELECT item FROM Item item WHERE item.categoryItem.id = :categoryItemId")
    List<Item> findByGroupItemId(@Param("categoryItemId") int groupItemId);

    @Modifying
    @Query("UPDATE Item SET stockQuantity = :stockQuantity, lastStockUpdate = :lastStockUpdate WHERE id = :id")
    void updateStockQuantity(@Param("id") final Long id, @Param("stockQuantity") int updateQuantity,
            @Param("lastStockUpdate") LocalDateTime lastStockUpdate);

    @Override
    default void updateStockQuantity(final Long id, final Integer quantity, final OperationTypeEnum operationTypeEnum)
            throws NonExistentEntityException, BusinessConstraintViolation {
        final Item itemFound = this.findById(id)
                .orElseThrow(() -> new NonExistentEntityException(String.format("O código %d não foi localizado.", id)));

        final int updateQuantity;
        if (operationTypeEnum.equals(OperationTypeEnum.OUT)) {
            if (itemFound.getStockQuantity() < quantity) {
                throw new BusinessConstraintViolation(
                        "Quantidade do estoque não pode ser menor que zero (0)",
                        "A quantidade á ser atualizada é maior que a quantidade atual no estoque");
            }
            updateQuantity = itemFound.getStockQuantity() - quantity;
        } else {
            updateQuantity = itemFound.getStockQuantity() + quantity;
        }

        this.updateStockQuantity(id, updateQuantity, LocalDateTime.now());
    }

}
