package com.github.eltonsandre.app.controller.departament;

import com.github.eltonsandre.app.controller.BaseSearchController;
import com.github.eltonsandre.app.controller.tablemodel.DepartmentTableModel;
import com.github.eltonsandre.app.core.domain.model.entity.Department;
import com.github.eltonsandre.app.core.domain.repository.DepartmentRepository;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/departament/departments-main.fxml", title = "departaments")
public class DepartmentController extends BaseSearchController<DepartmentTableModel, Department, Short> {

    @FXML TableColumn<DepartmentTableModel, String> colName;
    @FXML TableColumn<DepartmentTableModel, String> colPersonResponsible;

    public DepartmentController(final DepartmentRepository repository, final DepartmentRegisterController registerController) {
        super(repository, registerController);
    }

    @Override protected DepartmentTableModel newTableModel() {
        return new DepartmentTableModel();
    }

    @Override protected DepartmentTableModel newTableModel(final Department department) {
        return new DepartmentTableModel(department);
    }

    @Override protected void bindTableColums() {
        super.bindTableColums();

        this.colName.setCellValueFactory(cellData -> cellData.getValue().getName());
        this.colPersonResponsible.setCellValueFactory(cellData -> cellData.getValue().getPersonResponsible());
    }

}
