package com.github.eltonsandre.app.controller.supplier;

import com.github.eltonsandre.app.controller.BaseSearchController;
import com.github.eltonsandre.app.controller.tablemodel.SupplierTableModel;
import com.github.eltonsandre.app.core.domain.model.entity.Supplier;
import com.github.eltonsandre.app.core.domain.repository.SupplierRepository;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/supplier/supplier-main.fxml", title = "suppliers")
public class SupplierController extends BaseSearchController<SupplierTableModel, Supplier, Integer> {

    @FXML
    TableColumn<SupplierTableModel, String> colCompanyName;
    @FXML
    TableColumn<SupplierTableModel, String> colCnpj;
    @FXML
    TableColumn<SupplierTableModel, String> colPhoneNumber;

    public SupplierController(final SupplierRepository repository, final SupplierRegisterController registerController) {
        super(repository, registerController);
    }

    @Override
    protected SupplierTableModel newTableModel() {
        return new SupplierTableModel(new Supplier());
    }

    @Override
    protected SupplierTableModel newTableModel(final Supplier supplier) {
        return new SupplierTableModel(supplier);
    }

    @Override
    protected void bindTableColums() {
        super.bindTableColums();

        this.colCompanyName.setCellValueFactory(cellData -> cellData.getValue().companyNameProperty());
        this.colCnpj.setCellValueFactory(cellData -> cellData.getValue().cnpjProperty());
        this.colPhoneNumber.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
    }

}
