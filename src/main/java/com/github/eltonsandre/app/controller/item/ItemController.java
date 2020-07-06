package com.github.eltonsandre.app.controller.item;

import com.github.eltonsandre.app.controller.BaseSearchController;
import com.github.eltonsandre.app.controller.tablemodel.ItemTableModel;
import com.github.eltonsandre.app.core.domain.model.entity.Item;
import com.github.eltonsandre.app.core.domain.model.enuns.ItemSearchOptionEnum;
import com.github.eltonsandre.app.core.domain.repository.ItemRepository;
import com.jfoenix.controls.JFXComboBox;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/item/item-main.fxml", title = "itens")
public class ItemController extends BaseSearchController<ItemTableModel, Item, Long> {

    @FXML TableColumn<ItemTableModel, String> colName;
    @FXML TableColumn<ItemTableModel, Integer> colStockQuantity;
    @FXML TableColumn<ItemTableModel, String> colLastStockUpdate;

    @FXML JFXComboBox<ItemSearchOptionEnum> cboxSearchOptions;

    public ItemController(final ItemRepository repository, final ItemRegisterController registerController) {
        super(repository, registerController);
    }

    @Override protected ItemTableModel newTableModel() {
        return new ItemTableModel();
    }

    @Override protected ItemTableModel newTableModel(final Item item) {
        return new ItemTableModel(item);
    }

    @Override protected void initialize() {
        super.initialize();
        this.cboxSearchOptions.getItems().addAll(ItemSearchOptionEnum.values());
    }

    @Override protected void bindTableColums() {
        super.bindTableColums();

        this.colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.colStockQuantity.setCellValueFactory(cellData -> cellData.getValue().stockQuantityProperty().asObject());
        this.colLastStockUpdate.setCellValueFactory(cellData -> cellData.getValue().lastStockUpdateProperty());
    }

}
