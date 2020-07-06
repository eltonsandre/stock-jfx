package com.github.eltonsandre.app.core.domain.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockIssueItem extends BaseEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    Integer quantity;

    @JoinColumn(name = "itemId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Item item;

    @JoinColumn(name = "stockIssueId", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    StockIssue stockIssue;

}
