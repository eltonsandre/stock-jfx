package com.github.eltonsandre.app.core.domain.repository;

import com.github.eltonsandre.app.core.domain.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Short> {
}
