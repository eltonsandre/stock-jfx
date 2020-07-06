package com.github.eltonsandre.app.controller.category;

import com.github.eltonsandre.app.controller.BaseSearchController;
import com.github.eltonsandre.app.controller.tablemodel.CategoryItemTableModel;
import com.github.eltonsandre.app.core.domain.model.entity.CategoryItem;
import com.github.eltonsandre.app.core.domain.model.enuns.CategoryTypeEnum;
import com.github.eltonsandre.app.core.domain.repository.CategoryItemRepository;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/category/category-item-main.fxml", title = "categories")
public class CategoryItemController extends BaseSearchController<CategoryItemTableModel, CategoryItem, Integer> {

    @FXML TableColumn<CategoryItemTableModel, String> colName;
    @FXML TableColumn<CategoryItemTableModel, CategoryTypeEnum> colCategoryType;

    public CategoryItemController(final CategoryItemRepository repository, final CategoryItemRegistrationController registration) {
        super(repository, registration);
    }

    @Override
    protected CategoryItemTableModel newTableModel() {
        return new CategoryItemTableModel();
    }

    @Override
    protected CategoryItemTableModel newTableModel(final CategoryItem categoryItem) {
        return new CategoryItemTableModel(categoryItem);
    }

    @Override
    protected void bindTableColums() {
        super.bindTableColums();

        this.colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        this.colCategoryType.setCellValueFactory(cellData -> cellData.getValue().categoryTypeProperty());
    }


}