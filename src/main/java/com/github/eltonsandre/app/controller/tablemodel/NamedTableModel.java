package com.github.eltonsandre.app.controller.tablemodel;

import com.github.eltonsandre.app.core.domain.model.entity.NamedEntity;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class NamedTableModel<E extends NamedEntity<P>, P extends Serializable> extends BaseTableModel<E, P> {

    protected final StringProperty name = new SimpleStringProperty(this, "default.name");

    public NamedTableModel() {
        super();
    }

    public NamedTableModel(final String name) {
        this();

        this.name.set(name);
    }

    public NamedTableModel(final String name, final P id) {
        super(id);
        this.name.set(name);
    }

    public NamedTableModel(final E entity) {
        super(entity);
        this.name.set(entity.getName());
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(final String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") + ", name : " + this.name.get() + "}";
    }

    @Override
    public boolean filter(final Serializable filterText) {
        return this.getName().toUpperCase().contains(
                filterText.toString().toUpperCase());
    }

}
