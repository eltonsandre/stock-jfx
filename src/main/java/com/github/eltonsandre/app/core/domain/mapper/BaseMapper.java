package com.github.eltonsandre.app.core.domain.mapper;

/**
 * @author eltonsandre
 * date 03/05/2020 18:28
 */
public interface BaseMapper<E, D> {

    String componentModelSpring = "spring";

    E toEntity(D dto);

    D toDto(E entity);

}
