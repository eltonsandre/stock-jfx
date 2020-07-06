package com.github.eltonsandre.app.controller.tablemodel;

import com.github.eltonsandre.app.core.domain.model.entity.CategoryItem;
import com.github.eltonsandre.app.core.domain.model.entity.Item;
import com.github.eltonsandre.app.util.DateUtils;
import java.time.LocalDateTime;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ItemTableModel extends NamedTableModel<Item, Long> {

    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>(this, "createdAt");

    private final DoubleProperty costPrice = new SimpleDoubleProperty(this, "costPrice", 0);

    private final DoubleProperty salePrice = new SimpleDoubleProperty(this, "salePrice", 0);

    private final ReadOnlyIntegerWrapper stockQuantity = new ReadOnlyIntegerWrapper(this, "stockQuantity", 0);

    private final ReadOnlyObjectWrapper<String> lastStockUpdate = new ReadOnlyObjectWrapper<>(this, "lastStockUpdate");

    private final ObjectProperty<CategoryItem> groupItem = new SimpleObjectProperty<>(this, "groupItem");

    public ItemTableModel() {
        super(new Item());
    }

    public ItemTableModel(final Item item) {
        super(item);

        this.setCreatedAt(item.getCreatedAt());
        this.setCostPrice(item.getCostPrice().doubleValue());
        this.setSalePrice(item.getSalePrice().doubleValue());
        this.setStockQuantity(item.getStockQuantity());
        this.setLastStockUpdate(DateUtils.format(item.getLastStockUpdate()));
        this.setGroupItem(item.getCategoryItem());
    }

    public ItemTableModel(final String name, final CategoryItem categoryItem,
            final Double salePrice, final Integer stockQuantity,
            final LocalDateTime lastStockUpdate) {
        super(name);
        this.setGroupItem(categoryItem);
        this.setSalePrice(salePrice);
        this.setStockQuantity(stockQuantity);
        this.setLastStockUpdate(DateUtils.format(lastStockUpdate));
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt.get();
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return this.createdAt;
    }

    public Double getCostPrice() {
        return this.costPrice.get();
    }

    public void setCostPrice(final Double costPrice) {
        this.costPrice.set(costPrice);
    }

    public DoubleProperty costPriceProperty() {
        return this.costPrice;
    }

    public Double getSalePrice() {
        return this.salePrice.get();
    }

    public void setSalePrice(final Double salePrice) {
        this.salePrice.set(salePrice);
    }

    public DoubleProperty salePriceProperty() {
        return this.salePrice;
    }

    public Integer getStockQuantity() {
        return this.stockQuantity.get();
    }

    public void setStockQuantity(final Integer stockQuantity) {
        this.stockQuantity.set(stockQuantity);
    }

    public ReadOnlyIntegerWrapper stockQuantityProperty() {
        return this.stockQuantity;
    }

    public String getLastStockUpdate() {
        return this.lastStockUpdate.get();
    }

    public void setLastStockUpdate(final String lastStockUpdate) {
        this.lastStockUpdate.set(lastStockUpdate);
    }

    public ReadOnlyObjectWrapper<String> lastStockUpdateProperty() {
        return this.lastStockUpdate;
    }

    public CategoryItem getGroupItem() {
        return this.groupItem.get();
    }

    public void setGroupItem(final CategoryItem categoryItem) {
        this.groupItem.set(categoryItem);
    }

    public ObjectProperty<CategoryItem> groupItemProperty() {
        return this.groupItem;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
