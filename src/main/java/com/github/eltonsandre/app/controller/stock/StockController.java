package com.github.eltonsandre.app.controller.stock;

import com.github.eltonsandre.app.controller.tablemodel.StockItemTableModel;
import com.github.eltonsandre.app.core.domain.model.entity.Department;
import com.github.eltonsandre.app.core.domain.model.entity.Item;
import com.github.eltonsandre.app.core.domain.model.entity.StockIssue;
import com.github.eltonsandre.app.core.domain.model.entity.StockIssueItem;
import com.github.eltonsandre.app.core.domain.model.entity.StockReceipt;
import com.github.eltonsandre.app.core.domain.model.entity.StockReceiptItem;
import com.github.eltonsandre.app.core.domain.model.entity.Supplier;
import com.github.eltonsandre.app.core.domain.model.enuns.OperationTypeEnum;
import com.github.eltonsandre.app.core.domain.repository.DepartmentRepository;
import com.github.eltonsandre.app.core.domain.repository.ItemRepository;
import com.github.eltonsandre.app.core.domain.repository.StockIssueRepository;
import com.github.eltonsandre.app.core.domain.repository.StockReceiptRepository;
import com.github.eltonsandre.app.core.domain.repository.SupplierRepository;
import com.github.eltonsandre.app.jfx.util.dialog.DialogUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ViewController(value = "/fxml/stock/stock-main.fxml", title = "Estoque")
public class StockController {

    public static final String STYLE_ERROR = "-fx-border-color: red; -fx-border-width:  1.4";

    private static final String ANY_DIGIT = "\\d*";
    private static final String ANY_NON_DIGIT = "[\\D]";

    final DepartmentRepository departmentRepository;
    final ItemRepository itemRepository;
    final SupplierRepository supplierRepository;
    final StockReceiptRepository stockReceiptRepository;
    final StockIssueRepository stockIssueRepository;

    private final ObjectProperty<Item> searchedItem = new SimpleObjectProperty<>(this, "searchedItem");

    @FXML private JFXDatePicker txtReceiptDate;
    @FXML private JFXComboBox<Supplier> txtSupplier;
    @FXML private JFXComboBox<OperationTypeEnum> txtOperationType;
    @FXML private JFXComboBox<Department> txtDepartament;

    @FXML private JFXTextField txtItemId;
    @FXML private JFXTextField txtItemQuantity;
    @FXML private JFXTextField txtItemName;

    @FXML private JFXButton btnAddItem;
    @FXML private JFXButton btnResetItemFields;

    @FXML private TableView<StockItemTableModel> tbViewItems;
    @FXML private TableColumn<StockItemTableModel, Long> colItemId;
    @FXML private TableColumn<StockItemTableModel, String> colItemName;
    @FXML private TableColumn<StockItemTableModel, Integer> colItemQuantity;

    @FXML private JFXButton btnRemoveItem;
    @FXML private JFXButton btnFinish;
    @FXML private JFXButton btnNew;
    @FXML private GridPane gridPaneStockOperation;

    private List<Control> requiredFields = new ArrayList<>();
    private List<Control> stockItemRequiredFields = new ArrayList<>();

    @FXML
    void initialize() {
        this.txtOperationType.getItems().addAll(OperationTypeEnum.values());

        this.txtSupplier.getItems().addAll(this.supplierRepository.findAll());
        this.txtDepartament.getItems().addAll(this.departmentRepository.findAll());

        this.txtOperationType.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.handleOperationTypeChanged(oldValue, newValue));

        this.txtItemId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(ANY_DIGIT)) {
                this.txtItemId.setText(newValue.replaceAll(ANY_NON_DIGIT, ""));
            }
        });

        this.txtItemQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(ANY_DIGIT)) {
                this.txtItemQuantity.setText(newValue.replaceAll(ANY_NON_DIGIT, ""));
            }
        });

        this.colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        this.colItemName.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
        this.colItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

        this.tbViewItems.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.handleTableSelectionChanged(newValue));
        this.searchedItem.addListener((observable, oldValue, newValue) -> this.handleSearchedItemChanged(newValue));

        this.loadTooltips();
        this.initializeControls();
    }

    private void handleSearchedItemChanged(final Item newValue) {
        log.debug("newValue={}", newValue);
        if (this.searchedItem.get() == null) {
            this.txtItemId.clear();
            this.txtItemName.clear();
            this.txtItemQuantity.clear();
        }
    }

    private void handleTableSelectionChanged(final StockItemTableModel newValue) {
        this.btnRemoveItem.setDisable(newValue == null);
    }

    private List<Control> getRequiredFieldList() {
        final List<Control> controlsList = new ArrayList<>();

        controlsList.add(OperationTypeEnum.ENTRY.equals(this.txtOperationType.getSelectionModel().getSelectedItem()) ?
                this.txtSupplier :
                this.txtDepartament);
        controlsList.add(this.txtReceiptDate);
        controlsList.add(this.tbViewItems);

        return controlsList;
    }

    private List<Control> getStockItemRequiredFieldList() {
        List<Control> controlsList = new ArrayList<>();
        controlsList.add(this.txtItemName);
        controlsList.add(this.txtItemQuantity);

        return controlsList;
    }

    private void handleOperationTypeChanged(final OperationTypeEnum oldValue, final OperationTypeEnum newValue) {
        log.debug("oldValue={}, newValue={}", oldValue, newValue);
        txtSupplier.setPromptText(OperationTypeEnum.ENTRY.equals(newValue) ? "Fornecedor" : "Setor");

        if (newValue.equals(OperationTypeEnum.ENTRY)) {
            this.txtSupplier.toFront();
            this.requiredFields.add(this.txtSupplier);
            this.txtDepartament.toBack();
            this.txtDepartament.styleProperty().set(null);
            this.requiredFields.remove(this.txtDepartament);
        } else {
            this.txtDepartament.toFront();
            this.requiredFields.add(this.txtDepartament);
            this.txtSupplier.toBack();
            this.txtSupplier.styleProperty().set(null);
            this.requiredFields.remove(this.txtSupplier);
        }
    }

    @FXML
    void handleAddItem(final ActionEvent event) {
        if (hasInvalidFields()) {
            return;
        }

        final AtomicBoolean isItemDuplicated = new AtomicBoolean(false);

        this.tbViewItems.getItems().forEach(item -> isItemDuplicated.set(item.equals(new StockItemTableModel(this.searchedItem.get(), 0))));

        if (isItemDuplicated.get()) {
            DialogUtils.showWarning("Inclusão de item na lista", "Este item já está incluído na lista!");
            this.txtItemId.requestFocus();
            this.txtItemId.selectAll();
            return;
        }

        final int quantity = Integer.parseInt(this.txtItemQuantity.getText());
        final Item itemSearched = this.searchedItem.get();

        if (OperationTypeEnum.OUT.equals(this.txtOperationType.getSelectionModel().getSelectedItem())
                && itemSearched.getStockQuantity() < quantity) {
            DialogUtils.showWarning("Inclusão de item na lista",
                    String.format("A quantidade está acima do disponível no estoque. Este item tem %d unidade(s) disponível(is)!",
                            itemSearched.getStockQuantity()));
            this.txtItemQuantity.requestFocus();
            this.txtItemQuantity.selectAll();
        } else {
            this.tbViewItems.getItems().add(new StockItemTableModel(itemSearched, quantity));
            this.searchedItem.set(null);

        }
    }

    private boolean hasInvalidFields() {
        AtomicBoolean hasInvalidFields = new AtomicBoolean(false);

        this.stockItemRequiredFields.stream().filter(Objects::nonNull).forEach(control -> {
            control.setStyle(null);

            if (control instanceof JFXTextField) {
                hasInvalidFields.set(!((JFXTextField) control).validate());
            } else if (control instanceof JFXComboBox) {
                hasInvalidFields.set(!((JFXComboBox) control).validate());
            } else if (control instanceof JFXDatePicker) {
                hasInvalidFields.set(!((JFXDatePicker) control).validate());
            }
        });
        return hasInvalidFields.get();
    }

    @FXML
    void handleFinishButton(final ActionEvent event) {
        final AtomicBoolean hasInvalidFields = new AtomicBoolean(false);

        this.requiredFields.forEach(f -> {
            f.setStyle(null);

            if (f instanceof TableView<?>) {
                final TableView<?> textControl = (TableView<?>) f;

                if (textControl.itemsProperty().get().isEmpty()) {
                    textControl.setStyle(STYLE_ERROR);
                    hasInvalidFields.set(true);
                }
            } else if (f instanceof ComboBox) {
                final ComboBox<?> comboControl = (ComboBox<?>) f;

                if (comboControl.getSelectionModel().getSelectedItem() == null) {
                    comboControl.setStyle(STYLE_ERROR);
                    hasInvalidFields.set(true);
                }
            } else if (f instanceof DatePicker) {
                final DatePicker dateControl = (DatePicker) f;

                if (dateControl.getEditor().textProperty().isEmpty().get()) {
                    dateControl.setStyle(STYLE_ERROR);
                    hasInvalidFields.set(true);
                }
            }
        });

        if (hasInvalidFields.get()) {
            DialogUtils.showInfo("Conclusão do movimento no estoque", "Preencha os campos obrigatórios");
            return;
        }

        final OperationTypeEnum operationTypeEnum = this.txtOperationType
                .getSelectionModel().getSelectedItem();

        if (operationTypeEnum.equals(OperationTypeEnum.ENTRY)) {
            StockReceipt newStockReceipt = StockReceipt.builder()
                    .receiptDate(this.txtReceiptDate.getValue())
                    .supplier(this.txtSupplier.getSelectionModel().getSelectedItem())
                    .build();

            this.tbViewItems.getItems().forEach(
                    stockReceiptItem -> newStockReceipt.addStockReceiptItem(
                            StockReceiptItem.builder()
                                    .quantity(stockReceiptItem.getItemQuantity().shortValue())
                                    .item(stockReceiptItem.getEntity())
                                    .build()));

            this.stockReceiptRepository.save(newStockReceipt);
        } else {
            StockIssue newStockIssue = StockIssue.builder()
                    .issueDate(this.txtReceiptDate.getValue())
                    .department(this.txtDepartament.getSelectionModel().getSelectedItem()).build();

            this.tbViewItems.getItems().forEach(i -> newStockIssue.addStockIssueItem(StockIssueItem.builder()
                    .quantity(i.getItemQuantity())
                    .item(i.getEntity())
                    .build()));

            this.stockIssueRepository.save(newStockIssue);
        }

        this.tbViewItems.getItems().forEach(item -> {
            try {
                this.itemRepository.updateStockQuantity(item.getEntity().getId(), item.getItemQuantity(), operationTypeEnum);
            } catch (final Exception e) {
                log.error(e);
            }
        });

        this.gridPaneStockOperation.setDisable(true);
        DialogUtils.showInfo("Conclusão do movimento no estoque", "Movimentação do estoque concluída com sucesso!");
    }

    @FXML
    protected void actionItemSearchById(final ActionEvent event) {
        /*        if (this.txtItemId.getText() != null) { */
        if (!this.txtItemId.getText().isBlank()) {
            this.itemRepository.findById(Long.valueOf(this.txtItemId.getText())).ifPresentOrElse(item -> {
                        this.searchedItem.set(item);
                        this.txtItemName.setText(item.getName());
                        this.txtItemQuantity.requestFocus();
                    }, () -> {
                        this.txtItemId.requestFocus();
                        this.txtItemId.selectAll();
                        DialogUtils.showInfo("Pesquisa por item pelo Id...", "Nenhum item foi localizado");
                    }
            );

        }
    }

    @FXML
    void handleNewButton(final ActionEvent event) {
        this.initializeControls();
    }

    void initializeControls() {
        this.txtOperationType.getSelectionModel().select(OperationTypeEnum.ENTRY);
        this.txtReceiptDate.getEditor().clear();

        this.searchedItem.set(null);

        this.tbViewItems.getItems().clear();

        this.gridPaneStockOperation.setDisable(false);

        this.requiredFields = this.getRequiredFieldList();

        this.stockItemRequiredFields = this.getStockItemRequiredFieldList();
    }

    @FXML
    void actionRemoveItemButton(final ActionEvent event) {
        this.tbViewItems.getItems().remove(this.tbViewItems.getSelectionModel().getSelectedItem());
    }

    @FXML
    void actionResetItemFields(final ActionEvent event) {
        this.searchedItem.set(null);
    }

    private void loadTooltips() {
        this.txtItemId.setTooltip(new Tooltip("Digite o código do item e pressione ENTER para pesquisar!"));
        this.txtItemQuantity.setTooltip(new Tooltip("Digite a quantidade de itens á movimentar!"));
        this.btnAddItem.setTooltip(new Tooltip("Inclua o item pesquisado na lista!"));
        this.btnResetItemFields.setTooltip(new Tooltip("Limpe os valores digitados nos campos de pesquisa do item!"));
        this.btnRemoveItem.setTooltip(new Tooltip("Remova o item selecionado na lista!"));
        this.btnFinish.setTooltip(new Tooltip("Conclua e efetive o movimento no estoque!"));
        this.btnNew.setTooltip(new Tooltip("Inicie uma nova movimentação no estoque!"));
    }
}
