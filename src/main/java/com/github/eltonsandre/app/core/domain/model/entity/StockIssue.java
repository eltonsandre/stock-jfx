package com.github.eltonsandre.app.core.domain.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockIssue extends BaseEntity<Long> {

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDate issueDate;

    //    @Singular("addStockIssueItem")
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockIssue", fetch = FetchType.LAZY)
    List<StockIssueItem> stockIssueItens = new ArrayList<>();

    @JoinColumn(name = "departmentId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Department department;

    public void addStockIssueItem(final StockIssueItem item) {
        item.setStockIssue(this);
        this.stockIssueItens.add(item);
    }

}
