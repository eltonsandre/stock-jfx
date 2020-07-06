package com.github.eltonsandre.app.controller.tablemodel;

import com.github.eltonsandre.app.core.domain.model.entity.CategoryItem;
import com.github.eltonsandre.app.core.domain.model.enuns.CategoryTypeEnum;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CategoryItemTableModel extends NamedTableModel<CategoryItem, Integer> {

    @EqualsAndHashCode.Include
    private final ObjectProperty<CategoryTypeEnum> categoryType = new SimpleObjectProperty<>(this, "categoryType");

    private final StringProperty observation = new SimpleStringProperty(this, "default.observation");

    public CategoryItemTableModel() {
        super(new CategoryItem());
    }

    public CategoryItemTableModel(final CategoryItem categoryItem) {
        super(categoryItem);

        this.setCategoryType(categoryItem.getGroupType());
        this.setObservation(categoryItem.getObservation());
    }

    public CategoryItemTableModel(final String name, final CategoryTypeEnum categoryTypeEnum,
            final String observation) {
        super(name);
        this.setCategoryType(categoryTypeEnum);
        this.setObservation(observation);
    }

    public CategoryTypeEnum getCategoryType() {
        return this.categoryType.get();
    }

    public void setCategoryType(final CategoryTypeEnum categoryTypeEnum) {
        this.categoryType.set(categoryTypeEnum);
    }

    public ObjectProperty<CategoryTypeEnum> categoryTypeProperty() {
        return this.categoryType;
    }

    public String getObservation() {
        return this.observation.get();
    }

    public void setObservation(final String observation) {
        this.observation.set(observation);
    }

    public StringProperty observationProperty() {
        return this.observation;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
