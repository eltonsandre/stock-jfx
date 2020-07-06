package com.github.eltonsandre.app.controller.item;

import com.github.eltonsandre.app.controller.BaseRegistrationController;
import com.github.eltonsandre.app.core.domain.model.entity.CategoryItem;
import com.github.eltonsandre.app.core.domain.model.entity.Item;
import com.github.eltonsandre.app.core.domain.repository.CategoryItemRepository;
import com.github.eltonsandre.app.core.domain.repository.ItemRepository;
import com.github.eltonsandre.app.jfx.component.JFXCurrencyField;
import com.github.eltonsandre.app.jfx.util.FieldValidatorUtils;
import com.github.eltonsandre.app.util.DateUtils;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/item/item-register.fxml", title = "itens")
public class ItemRegisterController extends BaseRegistrationController<Item, Long> {

    final CategoryItemRepository categoryItemRepository;

    @FXML
    JFXTextField txtCreatedAt;
    @FXML
    JFXTextField txtLastUpdate;
    @FXML
    JFXTextField txtName;
    @FXML
    JFXComboBox<CategoryItem> cboxCategoryItem;

    @FXML
    JFXCurrencyField txtCostPrice;
    @FXML
    JFXCurrencyField txtSalePrice;

    @FXML
    JFXTextField txtStockQuantity;
    @FXML
    JFXTextField txtStockQuantityMin;

    public ItemRegisterController(final ResourceBundle resourceBundle, final ItemRepository repository,
                                  final CategoryItemRepository categoryItemRepository) {
        super(resourceBundle, repository);
        this.categoryItemRepository = categoryItemRepository;
    }

    @Override
    protected void loadRelatedData() {
        this.cboxCategoryItem.getItems().addAll(this.categoryItemRepository.findAll());
    }

    @Override
    protected void fillEntityFromFields(final Item item) {
        item.setName(this.txtName.getText());
        item.setCostPrice(this.txtCostPrice.getAmount());
        item.setSalePrice(this.txtSalePrice.getAmount());
        item.setStockQuantity(super.fieldParseIntSafe(this.txtStockQuantity));
        item.setStockQuantityMin(super.fieldParseIntSafe(this.txtStockQuantityMin));

        if (!this.txtLastUpdate.textProperty().isEmpty().get()) {
            item.setLastStockUpdate(DateUtils.parseDateTime(this.txtLastUpdate.getText()));
        }
        item.setCategoryItem(this.cboxCategoryItem.getSelectionModel().getSelectedItem());
    }

    @Override
    protected Item newRegister() {
        return new Item();
    }

    @Override
    protected Control getDefaultFocusField() {
        return this.txtName;
    }

    @Override
    protected void resetFieldsToEmpty() {
        super.resetFieldsToEmpty();

        this.txtCreatedAt.clear();
        this.txtLastUpdate.clear();
        this.txtName.clear();
        this.cboxCategoryItem.getSelectionModel().clearSelection();
        this.txtCostPrice.setZero();
        this.txtSalePrice.setZero();
        this.txtStockQuantity.clear();
        this.txtStockQuantityMin.clear();
    }

    @Override
    protected void fillFieldsFromEntity(final Item item) {
        this.txtName.setText(item.getName());
        this.txtCreatedAt.setText(DateUtils.format(item.getCreatedAt()));
        this.cboxCategoryItem.getSelectionModel().select(item.getCategoryItem());
        this.txtCostPrice.setAmount(item.getCostPrice());
        this.txtSalePrice.setAmount(item.getSalePrice());
        this.txtStockQuantity.setText(String.valueOf(item.getStockQuantity()));
        this.txtStockQuantityMin.setText(String.valueOf(item.getStockQuantityMin()));

        if (Objects.nonNull(item.getLastStockUpdate())) {
            this.txtLastUpdate.setText(DateUtils.format(item.getLastStockUpdate()));
        }
    }

    @Override
    protected void changedEditingModel(final Item newValue) {
        super.changedEditingModel(newValue);

        if (newValue!=null) {
            if (newValue.getId()==null) {
                this.changeStatusValueComponent(this.txtStockQuantity, false, true, 1);
                this.changeStatusValueComponent(this.txtStockQuantityMin, false, true, 1);
            } else {
                this.changeStatusValueComponent(this.txtStockQuantity, true, false, 0.5);
                this.changeStatusValueComponent(this.txtStockQuantityMin, true, false, 0.5);
            }
        }
    }

    private void changeStatusValueComponent(final JFXTextField jfxTextField, final boolean disabled, final boolean editable, final double opacityValue) {
        jfxTextField.setDisable(disabled);
        jfxTextField.setEditable(editable);
        jfxTextField.setOpacity(opacityValue);
    }


    @Override
    protected List<Control> getRequiredFieldList() {
        List<Control> controlsList = super.getRequiredFieldList();

        controlsList.add(this.txtName);
        controlsList.add(this.txtCostPrice);
        controlsList.add(this.txtSalePrice);
        controlsList.add(this.txtStockQuantity);
        controlsList.add(this.txtStockQuantityMin);
        controlsList.add(this.cboxCategoryItem);

        return controlsList;
    }

    @Override
    protected void validateFields() {
        this.txtName.getValidators().add(FieldValidatorUtils.requiredFieldValidator());

        this.txtCostPrice.getValidators().add(FieldValidatorUtils.currencyValidator());
        this.txtSalePrice.getValidators().add(FieldValidatorUtils.currencyValidator());
        this.txtStockQuantity.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
        this.txtStockQuantityMin.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
        this.cboxCategoryItem.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
    }

}
