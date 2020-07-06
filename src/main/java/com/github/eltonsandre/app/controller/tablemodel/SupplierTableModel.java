package com.github.eltonsandre.app.controller.tablemodel;

import com.github.eltonsandre.app.core.domain.model.entity.Supplier;
import com.github.eltonsandre.app.core.domain.model.enuns.UFEnum;
import java.io.Serializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SupplierTableModel extends BaseTableModel<Supplier, Integer> {

    private final StringProperty companyName = new SimpleStringProperty(this, "companyName");

    private final StringProperty tradingName = new SimpleStringProperty(this, "tradingName" );

    private final StringProperty cnpj = new SimpleStringProperty(this, "cnpj");

    private final StringProperty phoneNumber = new SimpleStringProperty(this, "phoneNumber");

    private final StringProperty emailAddress = new SimpleStringProperty(this, "emailAddress");

    private final StringProperty publicArea = new SimpleStringProperty(this, "publicArea");

    private final StringProperty district = new SimpleStringProperty(this, "district");

    private final StringProperty city = new SimpleStringProperty(this, "city");

    private final ObjectProperty<UFEnum> uf = new SimpleObjectProperty<>(this, "uf");

    private final StringProperty cep = new SimpleStringProperty(this, "cep");

    public SupplierTableModel() {
        super(new Supplier());
    }

    public SupplierTableModel(final Supplier supplier) {
        super(supplier);

        this.setCompanyName(supplier.getCompanyName());
        this.setTradingName(supplier.getTradingName());
        this.setCnpj(supplier.getCnpj());
        this.setPhoneNumber(supplier.getPhoneNumber());
        this.setEmailAddress(supplier.getEmailAddress());
        this.setPublicArea(supplier.getPublicArea());
        this.setDistrict(supplier.getDistrict());
        this.setCity(supplier.getCity());
        this.setUf(supplier.getUf());
        this.setCep(supplier.getCep());
    }

    public SupplierTableModel(final String tradingName, final String cnpj,
            final String phoneNumber) {
        this.setTradingName(tradingName);
        this.setCnpj(cnpj);
        this.setPhoneNumber(phoneNumber);
    }

    public String getCompanyName() {
        return this.companyName.get();
    }

    public void setCompanyName(final String companyName) {
        this.companyName.set(companyName);
    }

    public StringProperty companyNameProperty() {
        return this.companyName;
    }

    public String getTradingName() {
        return this.tradingName.get();
    }

    public void setTradingName(final String tradingName) {
        this.tradingName.set(tradingName);
    }

    public StringProperty tradingNameProperty() {
        return this.tradingName;
    }

    public String getCnpj() {
        return this.cnpj.get();
    }

    public void setCnpj(final String cnpj) {
        this.cnpj.set(cnpj);
    }

    public StringProperty cnpjProperty() {
        return this.cnpj;
    }

    public String getPhoneNumber() {
        return this.phoneNumber.get();
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty phoneNumberProperty() {
        return this.phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress.get();
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress.set(emailAddress);
    }

    public StringProperty emailAddressProperty() {
        return this.emailAddress;
    }

    public String getPublicArea() {
        return this.publicArea.get();
    }

    public void setPublicArea(final String publicArea) {
        this.publicArea.set(publicArea);
    }

    public StringProperty publicAreaProperty() {
        return this.publicArea;
    }

    public String getDistrict() {
        return this.district.get();
    }

    public void setDistrict(final String district) {
        this.district.set(district);
    }

    public StringProperty districtProperty() {
        return this.district;
    }

    public String getCity() {
        return this.city.get();
    }

    public void setCity(final String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return this.city;
    }

    public UFEnum getUf() {
        return this.uf.get();
    }

    public void setUf(final UFEnum UFEnum) {
        this.uf.set(UFEnum);
    }

    public ObjectProperty<UFEnum> ufProperty() {
        return this.uf;
    }

    public String getCep() {
        return this.cep.get();
    }

    public void setCep(final String cep) {
        this.cep.set(cep);
    }

    public StringProperty cepProperty() {
        return this.cep;
    }

    @Override
    public String toString() {
        return this.getEntity().toString();
    }

    @Override
    public boolean filter(final Serializable filterText) {
        return new StringBuffer(this.getCompanyName().toUpperCase())
                .append(this.getCnpj()).toString()
                .contains(filterText.toString().toUpperCase());
    }

}
