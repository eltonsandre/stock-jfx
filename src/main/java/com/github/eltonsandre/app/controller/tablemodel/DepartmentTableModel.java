package com.github.eltonsandre.app.controller.tablemodel;

import com.github.eltonsandre.app.core.domain.model.entity.Department;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class DepartmentTableModel extends BaseTableModel<Department, Short> {

    protected final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty personResponsible = new SimpleStringProperty(this, "personResponsible");

    public DepartmentTableModel() {
        super(new Department());
    }

    public DepartmentTableModel(final Department department) {
        super(department);

        this.name.set(department.getName());
        this.personResponsible.set(department.getPersonResponsible());
    }

    @Override public boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return this.name.getValue();
    }

}
