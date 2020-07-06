package com.github.eltonsandre.app.core.domain.repository;

import com.github.eltonsandre.app.core.domain.model.entity.StockIssue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockIssueRepository extends JpaRepository<StockIssue, Long> {

    @Query("SELECT s FROM StockIssue s WHERE s.createdAt = :createdAt")
    Optional<StockIssue> findByCreateAt(@Param("createdAt")final LocalDateTime createdAt);

    @Query("SELECT s FROM StockIssue s WHERE s.issueDate = :issueDate")
    Stream<StockIssue> findByIssueDate(@Param("issueDate") final LocalDate issueDate);


}
