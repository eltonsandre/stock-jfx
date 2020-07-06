package com.github.eltonsandre.app.controller.departament;

import com.github.eltonsandre.app.controller.BaseRegistrationController;
import com.github.eltonsandre.app.core.domain.model.entity.Department;
import com.github.eltonsandre.app.core.domain.repository.DepartmentRepository;
import com.github.eltonsandre.app.jfx.util.FieldValidatorUtils;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/departament/department-register.fxml", title = "departament")
public class DepartmentRegisterController extends BaseRegistrationController<Department, Short> {

    @FXML JFXTextField txtName;
    @FXML JFXTextField txtPersonResponsible;
    @FXML JFXTextArea txtDescription;

    public DepartmentRegisterController(final ResourceBundle resourceBundle, final DepartmentRepository repository) {
        super(resourceBundle, repository);
    }

    @Override protected void fillEntityFromFields(final Department department) {
        department.setName(this.txtName.getText());
        department.setPersonResponsible(this.txtPersonResponsible.getText());
        department.setDescription(this.txtDescription.getText());
    }

    @Override protected Department newRegister() {
        return new Department();
    }

    @Override protected Control getDefaultFocusField() {
        return txtName;
    }

    @Override protected void loadRelatedData() {
    }

    @Override protected void resetFieldsToEmpty() {
        super.resetFieldsToEmpty();

        this.txtName.clear();
        this.txtDescription.clear();
        this.txtPersonResponsible.clear();
    }

    @Override protected void fillFieldsFromEntity(final Department department) {
        super.fillFieldsFromEntity(department);

        this.txtName.setText(department.getName());
        this.txtPersonResponsible.setText(department.getPersonResponsible());
        this.txtDescription.setText(department.getDescription());
    }

    @Override protected List<Control> getRequiredFieldList() {
        List<Control> controlsList = new ArrayList<>();

        controlsList.add(this.txtPersonResponsible);
        controlsList.add(this.txtName);

        return controlsList;
    }

    @Override protected void validateFields() {
        this.txtName.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
        this.txtPersonResponsible.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
    }

}
