package com.github.eltonsandre.app.core.domain.repository;

import com.github.eltonsandre.app.core.domain.model.entity.StockIssue;
import com.github.eltonsandre.app.core.domain.model.entity.StockIssueItem;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockIssueItemRepository extends JpaRepository<StockIssue, Long> {

    @Query("SELECT s FROM StockIssueItem s WHERE s.stockIssue.id = :stockIssueId")
    StockIssueItem findByStockIssuetId(@Param("stockIssueId") final int stockIssueId);

    @Query("SELECT s FROM StockIssueItem s WHERE s.quantity = :quantity")
    Stream<StockIssueItem> findByQuantity(@Param("quantity") final int quantity);


}
