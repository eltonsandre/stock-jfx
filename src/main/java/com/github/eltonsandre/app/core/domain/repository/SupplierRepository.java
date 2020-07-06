package com.github.eltonsandre.app.core.domain.repository;

import com.github.eltonsandre.app.core.domain.model.entity.Supplier;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    List<Supplier> findByCompanyName(String companyName);

}
