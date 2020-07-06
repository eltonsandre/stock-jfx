package com.github.eltonsandre.app.controller.category;

import com.github.eltonsandre.app.controller.BaseRegistrationController;
import com.github.eltonsandre.app.core.domain.model.entity.CategoryItem;
import com.github.eltonsandre.app.core.domain.model.enuns.CategoryTypeEnum;
import com.github.eltonsandre.app.core.domain.repository.CategoryItemRepository;
import com.github.eltonsandre.app.jfx.util.FieldValidatorUtils;
import com.jfoenix.controls.JFXComboBox;
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
import org.springframework.stereotype.Controller;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/category/category-item-register.fxml", title = "categories")
public class CategoryItemRegistrationController extends BaseRegistrationController<CategoryItem, Integer> {

    @FXML JFXTextField txtName;
    @FXML JFXComboBox<CategoryTypeEnum> cboxCategoryType;
    @FXML JFXTextArea txtObservation;

    public CategoryItemRegistrationController(final ResourceBundle resourceBundle, final CategoryItemRepository repository) {
        super(resourceBundle, repository);
    }

    @Override
    protected void fillEntityFromFields(final CategoryItem categoryItem) {
        categoryItem.setName(this.txtName.getText());
        categoryItem.setGroupType(this.cboxCategoryType.getSelectionModel().getSelectedItem());
        categoryItem.setObservation(this.txtObservation.getText());
    }

    @Override protected CategoryItem newRegister() {
        return new CategoryItem();
    }

    @Override
    protected void loadRelatedData() {
        this.cboxCategoryType.getItems().addAll(CategoryTypeEnum.values());
    }

    @Override
    protected void resetFieldsToEmpty() {
        super.resetFieldsToEmpty();

        this.txtName.clear();
        this.cboxCategoryType.getSelectionModel().clearSelection();
        this.txtObservation.clear();
    }

    @Override
    protected void fillFieldsFromEntity(final CategoryItem entity) {
        super.fillFieldsFromEntity(entity);

        this.txtName.setText(entity.getName());
        this.cboxCategoryType.getSelectionModel().select(entity.getGroupType());
        this.txtObservation.setText(entity.getObservation());
    }

    @Override
    protected List<Control> getRequiredFieldList() {
        final List<Control> controlsList = new ArrayList<>();
        controlsList.add(this.txtName);
        controlsList.add(this.cboxCategoryType);

        return controlsList;
    }

    @Override
    protected Control getDefaultFocusField() {
        return this.txtName;
    }

    @Override
    protected void validateFields() {
        this.txtName.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
    }
}