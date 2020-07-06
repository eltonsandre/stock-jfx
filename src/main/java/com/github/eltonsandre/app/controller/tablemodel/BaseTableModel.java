package com.github.eltonsandre.app.controller.tablemodel;

import com.github.eltonsandre.app.core.domain.model.entity.BaseEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import javafx.beans.property.ReadOnlyObjectWrapper;

public abstract class BaseTableModel<E extends BaseEntity<P>, P extends Serializable> {

    protected final ReadOnlyObjectWrapper<P> id = new ReadOnlyObjectWrapper<>(this, "id");

    private final ReadOnlyObjectWrapper<E> entity = new ReadOnlyObjectWrapper<>(this, "entity");

    private final Class<E> entityClass;

    @SuppressWarnings(value = "unchecked")
    public BaseTableModel() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public BaseTableModel(final P id) {
        this();
        this.id.set(id);
    }

    public BaseTableModel(final E entity) {
        this();
        this.entity.set(entity);
        this.id.set(entity.getId());
    }

    public P getId() {
        return this.id.get();
    }

    public void setId(final P id) {
        this.id.set(id);
    }

    public ReadOnlyObjectWrapper<P> idProperty() {
        return this.id;
    }

    public E getEntity() {
        return this.entity.get();
    }

    public void setEntity(final E entity) {
        this.entity.set(entity);
    }

    public ReadOnlyObjectWrapper<E> entityProperty() {
        return this.entity;
    }

    public Class<E> getEntityClass() {
        return this.entityClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object obj) {
        if (obj != null && this.getClass() == obj.getClass()) {
            final BaseTableModel<E, P> other = (BaseTableModel<E, P>) obj;

            if (this.entityClass != null && other.entityClass != null && this.entityClass.equals(other.entityClass)) {
                return this.idProperty() != null
                        && other.idProperty() != null
                        && this.id.get() != null && other.id.get() != null
                        && this.id.get().equals(other.idProperty().get());
            }
        }

        return false;
    }

    @Override public int hashCode() {
        return Objects.hash(id.get());
    }

    @Override
    public String toString() {
        return this.getEntityClass().getSimpleName() + " {id : " + String.valueOf(this.id.get()) + "}";
    }

    public boolean filter(final Serializable filterText) {
        return this.getId().equals(filterText);
    }

}
