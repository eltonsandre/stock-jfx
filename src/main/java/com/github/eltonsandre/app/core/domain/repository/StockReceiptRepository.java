package com.github.eltonsandre.app.core.domain.repository;

import com.github.eltonsandre.app.core.domain.model.entity.StockReceipt;
import com.github.eltonsandre.app.core.domain.model.entity.StockReceiptItem;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockReceiptRepository extends JpaRepository<StockReceipt, Long> {

    @Query("SELECT s FROM StockReceiptItem s WHERE s.stockReceipt.id = :stockReceiptId")
    Stream<StockReceiptItem> findByStockReceiptId(@Param("stockReceiptId") final Long stockReceiptId);

    @Query("SELECT s FROM StockReceiptItem s WHERE s.quantity = :quantity")
    Stream<StockReceiptItem> findByQuantity(@Param("quantity") final int quantity);

}
