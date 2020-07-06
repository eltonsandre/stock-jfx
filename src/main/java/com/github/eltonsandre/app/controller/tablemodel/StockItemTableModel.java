package com.github.eltonsandre.app.controller.tablemodel;

import com.github.eltonsandre.app.core.domain.model.entity.Item;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;

public class StockItemTableModel extends NamedTableModel<Item, Long> {

    private final ReadOnlyLongWrapper itemId = new ReadOnlyLongWrapper(this, "itemId", 0);

    private final ReadOnlyStringWrapper itemName = new ReadOnlyStringWrapper(this, "itemName");

    private final ReadOnlyIntegerWrapper itemQuantity = new ReadOnlyIntegerWrapper(this, "itemQuantity", 0);

    private final ObjectProperty<Item> item = new SimpleObjectProperty<>(this, "item");

    public StockItemTableModel(final Item item, final Integer quantity) {
        super(item);

        this.setItemId(item.getId());
        this.setItemName(item.getName());
        this.setItemQuantity(quantity);
    }

    public Long getItemId() {
        return this.itemId.get();
    }

    public void setItemId(final Long itemId) {
        this.itemId.set(itemId);
    }

    public ReadOnlyLongWrapper itemIdProperty() {
        return this.itemId;
    }

    public String getItemName() {
        return this.itemName.get();
    }

    public void setItemName(final String itemName) {
        this.itemName.set(itemName);
    }

    public ReadOnlyStringWrapper itemNameProperty() {
        return this.itemName;
    }

    public Integer getItemQuantity() {
        return this.itemQuantity.get();
    }

    public void setItemQuantity(final Integer itemQuantity) {
        this.itemQuantity.set(itemQuantity);
    }

    public ReadOnlyIntegerWrapper itemQuantityProperty() {
        return this.itemQuantity;
    }

    public Item getItem() {
        return this.item.get();
    }

    public void setItem(final Item item) {
        this.item.set(item);
    }

    public ObjectProperty<Item> itemProperty() {
        return this.item;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        StockItemTableModel other = (StockItemTableModel) obj;
        if (this.itemId == null) {
            if (other.itemId != null) {
                return false;
            }
        } else if (this.itemId.get() != other.itemId.get()) {
            return false;
        }
        return true;
    }
}