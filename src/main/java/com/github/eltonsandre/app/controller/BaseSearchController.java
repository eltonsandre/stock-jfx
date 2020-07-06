package com.github.eltonsandre.app.controller;

import com.github.eltonsandre.app.controller.components.TooltipUtils;
import com.github.eltonsandre.app.controller.tablemodel.BaseTableModel;
import com.github.eltonsandre.app.core.domain.model.entity.BaseEntity;
import com.github.eltonsandre.app.jfx.util.dialog.DialogUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import java.io.Serializable;
import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

@Log4j2
@FieldDefaults(level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class BaseSearchController<T extends BaseTableModel<? extends BaseEntity<P>, ? extends Serializable>, E extends BaseEntity<P>, P extends Serializable> {

    static final int ROWS_PER_PAGE = 5;

    final JpaRepository<E, P> repository;
    final BaseRegistrationController<E, P> registrationController;

    final ObservableList<T> observableModels = FXCollections.observableArrayList();

    @FXML JFXTabPane tabPane;
    @FXML Tab tabSearch;
    @FXML Tab tabRegister;

    @FXML JFXTextField txtSearch;

    @FXML TableView<T> tableView;
    @FXML TableColumn<T, P> colId;

    @FXML JFXButton btnNew;
    @FXML JFXButton btnEdit;
    @FXML JFXButton btnDelete;

    @FXML Pagination pagination;
    int currentPageIndex = 0;
    long totalElements = 0;

    ObjectProperty<T> editingModel = new SimpleObjectProperty<>(this, "editingModel");
    FilteredList<T> filterableModels;
    SortedList<T> listSortedModel;

    @Setter(value = AccessLevel.PROTECTED)
    @Getter(value = AccessLevel.PROTECTED)
    String nameView;

    @Setter(value = AccessLevel.PROTECTED)
    Stage stage;

    @FXML protected void actionDelete(final ActionEvent event) {
        log.info("{}", event.getTarget());
        Optional<ButtonType> result = DialogUtils.showConfirmation(
                String.format("Confirmação de exclusão de %s", this.nameView),
                String.format("Confirma exclusão do %s selecionado?", this.nameView));

        result.filter(ButtonType.YES::equals).ifPresent(buttonType -> {
            T seletecModelItem = this.tableView.getSelectionModel().selectedItemProperty().getValue();

            if (this.editingModel.get() != null && this.editingModel.get().equals(seletecModelItem)) {
                this.editingModel.set(null);
                this.registrationController.btnSave.setDisable(true);
            }

            this.repository.delete((E) seletecModelItem.getEntity());

            this.observableModels.remove(seletecModelItem);

            DialogUtils.showInfo(String.format("Exclusão de %s", this.nameView),
                    String.format("O %s selecionado foi excluído com sucesso", this.nameView));
        });
    }

    @FXML protected void actionEdit(final ActionEvent event) {
        this.managedEntity((E) this.tableView.getSelectionModel().selectedItemProperty().getValue().getEntity());
    }

    @FXML protected void actionNew(final ActionEvent event) {
        managedEntity((E) this.newTableModel().getEntity());
    }

    private void managedEntity(E entity) {
        registrationController.setEditEntity(entity, entitySaved -> {
            this.refressTable(this.currentPageIndex);

            this.tableView.getSelectionModel().selectLast();
            this.tabPane.getSelectionModel().select(this.tabSearch);
        });

        this.tabPane.getSelectionModel().select(this.tabRegister);
    }

    @FXML protected void actionSearch(final KeyEvent e) {
        this.filterableModels.setPredicate(p -> p.filter(this.txtSearch.getText()));
    }

    protected void changedTableSelection(final T newValue) {
        this.btnEdit.setDisable(newValue == null);
        this.btnDelete.setDisable(newValue == null);
    }

    @PostConstruct
    public void init() {
        final ViewController annotation = this.getClass().getAnnotation(ViewController.class);
        if (annotation != null && StringUtils.isNotBlank(annotation.title())) {
            nameView = annotation.title();
        }

        log.debug("controller={}", this.nameView);
    }

    @FXML protected void initialize() {
        log.debug("inicializa search");
        this.loadTooltips();
        this.bindTableColums();

        this.refressTable(0);

        this.pagination.currentPageIndexProperty()
                .addListener((observable, oldValue, newValue) -> this.refressTable(newValue.intValue()));

        this.filterableModels = new FilteredList<>(this.observableModels, p -> true);

        this.listSortedModel = new SortedList<>(this.filterableModels);
        this.listSortedModel.comparatorProperty().bind(this.tableView.comparatorProperty());

        this.tableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.changedTableSelection(newValue));

        this.tableView.setItems(this.listSortedModel);

        this.tableView.getSelectionModel().selectFirst();
    }

    private void refressTable(final int pageNumber) {
        if (!this.observableModels.isEmpty()) {
            this.observableModels.clear();
        }

        this.currentPageIndex = pageNumber;

        final Page<E> result = this.repository.findAll(PageRequest.of(pageNumber, ROWS_PER_PAGE));

        this.pagination.setPageCount(result.getTotalPages() > 0 ? result.getTotalPages() : 1);
        this.pagination.setCurrentPageIndex(pageNumber);
        this.pagination.setVisible(!result.isEmpty());

        this.totalElements = result.getTotalElements();

        result.getContent()
                .stream()
                .map(this::newTableModel)
                .forEach(this.observableModels::add);
    }

    protected void bindTableColums() {
        if (this.colId != null) {
            this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        }
    }

    public void loadTooltips() {
        this.txtSearch.setTooltip(TooltipUtils.TOOLTIP_BUTTON_SEARCH);
        this.btnEdit.setTooltip(TooltipUtils.TOOLTIP_BUTTON_EDIT);
        this.btnDelete.setTooltip(TooltipUtils.TOOLTIP_BUTTON_DELETE);
        this.btnNew.setTooltip(TooltipUtils.TOOLTIP_BUTTON_NEW);
    }

    protected abstract T newTableModel();

    protected abstract T newTableModel(E entity);

}
