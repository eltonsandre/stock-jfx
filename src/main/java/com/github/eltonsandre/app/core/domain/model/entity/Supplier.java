package com.github.eltonsandre.app.core.domain.model.entity;

import com.github.eltonsandre.app.core.domain.model.enuns.UFEnum;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Supplier extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 120)
    private String tradingName;

    @Column(nullable = false, length = 14, unique = true)
    private String cnpj;

    @Column(length = 11)
    private String phoneNumber;

    @Column(length = 120, unique = true)
    private String emailAddress;

    @Column(length = 100)
    private String publicArea;

    @Column(length = 80)
    private String district;

    @Column(length = 80)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(length = 2)
    private UFEnum uf;

    @Column(nullable = false, length = 8)
    private String cep;

    @Override public String toString() {
        return this.companyName;
    }
}