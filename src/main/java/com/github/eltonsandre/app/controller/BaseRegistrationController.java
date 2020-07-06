package com.github.eltonsandre.app.controller;

import com.github.eltonsandre.app.controller.components.TooltipUtils;
import com.github.eltonsandre.app.core.domain.model.entity.BaseEntity;
import com.github.eltonsandre.app.jfx.util.dialog.DialogUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import io.vavr.control.Try;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class BaseRegistrationController<E extends BaseEntity<P>, P extends Serializable> {

    @Qualifier("bundle-i18n")
    final ResourceBundle resourceBundle;

    final JpaRepository<E, P> repository;
    final ObjectProperty<E> editingEntityModel = new SimpleObjectProperty<>(this, "editingEntityModel");

    @FXML
    URL location;
    @FXML
    JFXTextField txtId;
    @FXML
    JFXButton btnNew;
    @FXML
    JFXButton btnSave;

    List<Control> requiredFields = new ArrayList<>();
    Control controlDefaultFocus;

    @Setter(value = AccessLevel.PROTECTED)
    @Getter(value = AccessLevel.PROTECTED)
    String nameView;

    @Setter(value = AccessLevel.PROTECTED)
    Stage stage;

    private Consumer<E> callbackSave;

    @FXML
    protected void actionNew(final ActionEvent event) {
        this.setEditEntity(this.newRegister());
    }

    protected void setEditEntity(final E entity) {
        this.editingEntityModel.set(entity);
    }

    protected void setEditEntity(final E entity, Consumer<E> callbackSave) {
        this.editingEntityModel.set(entity);
        this.callbackSave = callbackSave;
    }

    @FXML
    protected void actionSave(final ActionEvent event) {
        if (this.hasAnyInvalidFields()) {
            return;
        }

        final E entity = this.editingEntityModel.get();

        this.fillEntityFromFields(entity);

        boolean isCreateEntity = entity.getId() == null;

        try {
            this.repository.save(entity);
        } catch (final ConstraintViolationException e) {
            log.error(e);
        } catch (final org.hibernate.exception.DataException e) {
            log.error(e);
            DialogUtils.showError(this.stage, "Erro", e.getCause().getMessage());
            return;
        } catch (final org.springframework.dao.DataIntegrityViolationException e) {
            log.error(e);
            DialogUtils.showError(this.stage, String.format("Erro: Este %s já existe", this.nameView),
                    String.format("Já existe um %s com esse mesmo nome.%ncausa: %s ", this.nameView, e.getCause().getMessage()));
            return;
        }

        this.btnSave.setDisable(true);

        if (isCreateEntity) {
            DialogUtils.showInfo(String.format("Criação de %s", this.nameView),
                    String.format("O %s foi criado com sucesso", this.nameView));
        } else {
            DialogUtils.showInfo(String.format("Atualização de %s", this.nameView),
                    String.format("O %s foi atualizado com sucesso", this.nameView));
        }

        if (this.callbackSave != null) {
            this.callbackSave.accept(entity);
        }
    }

    protected boolean hasAnyInvalidFields() {
        final AtomicBoolean hasAnyInvalid = new AtomicBoolean(false);

        this.requiredFields.stream().filter(Objects::nonNull).forEach(control -> {
            control.setStyle(null);

            if (control instanceof JFXTextField) {
                hasAnyInvalid.set(!((JFXTextField) control).validate());
            } else if (control instanceof JFXComboBox) {
                hasAnyInvalid.set(!((JFXComboBox) control).validate());
            } else if (control instanceof JFXDatePicker) {
                hasAnyInvalid.set(!((JFXDatePicker) control).validate());
            }
        });

        return hasAnyInvalid.get();
    }

    protected void changedEditingModel(final E newValue) {
        log.debug("newValue={}", newValue);
        this.requiredFields.stream().filter(Objects::nonNull).forEach(control -> control.setStyle(null));

        this.resetFieldsToEmpty();
        if (newValue.getId() != null) {
            this.fillFieldsFromEntity(newValue);
        }

        this.btnSave.setDisable(false);
        this.controlDefaultFocus.requestFocus();
    }

    @PostConstruct
    public void init() {
        final ViewController annotation = this.getClass().getAnnotation(ViewController.class);
        if (annotation != null && StringUtils.isNotBlank(annotation.title())) {
            this.nameView = Try.of(() -> this.resourceBundle.getString(annotation.title()))
                    .onFailure(throwable -> log.error("key={}", annotation.title()))
                    .getOrElse(annotation.title());
        }

        log.debug("controller={}", this.nameView);
    }

    @FXML
    protected void initialize() {
        this.loadTooltips();
        this.loadRelatedData();
        this.validateFields();

        this.editingEntityModel.addListener((observable, oldValue, newValue) -> this.changedEditingModel(newValue));

        this.controlDefaultFocus = this.getDefaultFocusField();
        this.requiredFields.addAll(this.getRequiredFieldList());
    }

    public void loadTooltips() {
        this.btnNew.setTooltip(TooltipUtils.TOOLTIP_BUTTON_NEW);
        this.btnSave.setTooltip(TooltipUtils.TOOLTIP_BUTTON_SAVE);
    }

    protected void resetFieldsToEmpty() {
        this.txtId.clear();
    }

    protected int fieldParseIntSafe(final TextField control) {
        try {
            return Integer.parseInt(control.getText());
        } catch (final NumberFormatException nex) {
            return 0;
        }
    }

    protected void fillFieldsFromEntity(final E entity) {
        this.txtId.setText(String.valueOf(entity.getId()));
    }

    protected List<Control> getRequiredFieldList() {
        return new ArrayList<>();
    }

    protected abstract void fillEntityFromFields(E entity);

    protected abstract E newRegister();

    protected abstract Control getDefaultFocusField();

    protected abstract void loadRelatedData();

    protected abstract void validateFields();

}
