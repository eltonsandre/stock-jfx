package com.github.eltonsandre.app.core.domain.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedQueries({
        @NamedQuery(name = "StockReceipt.findByCreateAt", query = "SELECT s FROM StockReceipt s WHERE s.createdAt = :createdAt"),
        @NamedQuery(name = "StockReceipt.findByReceiptDate", query = "SELECT s FROM StockReceipt s WHERE s.receiptDate = :receiptDate") })
public class StockReceipt extends BaseEntity<Long> {

    @CreationTimestamp
    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdate;

    @Column(nullable = false)
    private LocalDate receiptDate;

    @Singular("stockReceiptItem")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockReceipt", fetch = FetchType.LAZY)
    private List<StockReceiptItem> stockReceiptItemList;

    @JoinColumn(name = "supplierId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Supplier supplier;

    public void addStockReceiptItem(final StockReceiptItem item) {
        item.setStockReceipt(this);
        this.stockReceiptItemList.add(item);
    }

}
