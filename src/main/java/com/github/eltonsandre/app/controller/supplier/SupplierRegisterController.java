package com.github.eltonsandre.app.controller.supplier;

import com.github.eltonsandre.app.controller.BaseRegistrationController;
import com.github.eltonsandre.app.core.domain.model.entity.Supplier;
import com.github.eltonsandre.app.core.domain.model.enuns.UFEnum;
import com.github.eltonsandre.app.core.domain.repository.SupplierRepository;
import com.github.eltonsandre.app.jfx.util.FieldValidatorUtils;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.ResourceBundle;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/supplier/supplier-register.fxml", title = "supplier")
public class SupplierRegisterController extends BaseRegistrationController<Supplier, Integer> {

    @FXML
    private JFXTextField txtTradingName;
    @FXML
    private JFXTextField txtCompanyName;
    @FXML
    private JFXTextField txtCnpj;
    @FXML
    private JFXTextField txtPhoneNumber;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtPublicArea;
    @FXML
    private JFXTextField txtDistrict;
    @FXML
    private JFXTextField txtCity;
    @FXML
    private JFXComboBox<UFEnum> cmbBoxUF;
    @FXML
    private JFXTextField txtCep;

    public SupplierRegisterController(final ResourceBundle bundle, final SupplierRepository repository) {
        super(bundle, repository);
    }

    @Override
    protected void fillEntityFromFields(final Supplier supplier) {
        supplier.setCompanyName(this.txtCompanyName.getText());
        supplier.setTradingName(this.txtTradingName.getText());
        supplier.setCnpj(this.txtCnpj.getText());
        supplier.setPublicArea(this.txtPublicArea.getText());
        supplier.setDistrict(this.txtDistrict.getText());
        supplier.setCity(this.txtCity.getText());
        supplier.setUf(this.cmbBoxUF.getSelectionModel().getSelectedItem());
        supplier.setCep(this.txtCep.getText());
        supplier.setPhoneNumber(this.txtPhoneNumber.getText());
        supplier.setEmailAddress(this.txtEmail.getText());
    }

    @Override
    protected Supplier newRegister() {
        return new Supplier();
    }

    @Override
    protected Control getDefaultFocusField() {
        return this.txtTradingName;
    }

    @Override
    protected void resetFieldsToEmpty() {
        super.resetFieldsToEmpty();

        this.txtCompanyName.clear();
        this.txtTradingName.clear();
        this.txtCnpj.clear();
        this.txtPublicArea.clear();
        this.txtDistrict.clear();
        this.txtCity.clear();
        this.cmbBoxUF.getSelectionModel().clearSelection();
        this.txtCep.clear();
        this.txtPhoneNumber.clear();
        this.txtEmail.clear();
    }

    @Override
    protected void fillFieldsFromEntity(final Supplier supplier) {
        super.fillFieldsFromEntity(supplier);

        this.txtCompanyName.setText(supplier.getCompanyName());
        this.txtTradingName.setText(supplier.getTradingName());
        this.txtCnpj.setText(supplier.getCnpj());
        this.txtPublicArea.setText(supplier.getPublicArea());
        this.txtDistrict.setText(supplier.getDistrict());
        this.txtCity.setText(supplier.getCity());
        this.cmbBoxUF.getSelectionModel().select(supplier.getUf());
        this.txtCep.setText(supplier.getCep());
        this.txtPhoneNumber.setText(supplier.getPhoneNumber());
        this.txtEmail.setText(supplier.getEmailAddress());
    }

    @Override
    protected void loadRelatedData() {
        this.cmbBoxUF.getItems().addAll(UFEnum.values());
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.txtCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.txtCnpj.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        this.txtPhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.txtPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        this.txtCep.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                this.txtCep.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @Override
    protected List<Control> getRequiredFieldList() {
        return List.of(
                this.txtCompanyName,
                this.txtTradingName,
                this.txtCnpj,
                this.txtPhoneNumber,
                this.txtEmail,
                this.txtCep,
                this.txtPublicArea,
                this.txtDistrict,
                this.txtCity,
                this.cmbBoxUF);
    }

    @Override
    protected void validateFields() {
        FieldValidatorUtils.requiredFieldsValidator(
                List.of(this.txtCompanyName,
                        this.txtTradingName,
                        this.txtCnpj,
                        this.txtPhoneNumber,
                        this.txtEmail,
                        this.txtCep,
                        this.txtPublicArea,
                        this.txtDistrict,
                        this.txtCity,
                        this.cmbBoxUF
                ));
        //        this.txtCompanyName.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtTradingName.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtPhoneNumber.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtEmail.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtCnpj.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtCep.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtPublicArea.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtDistrict.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.txtCity.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
//        this.cmbBoxUF.getValidators().add(FieldValidatorUtils.requiredFieldValidator());
    }

}
