package com.github.eltonsandre.app.jfx.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eltonsandre
 * date: 3 de nov de 2017 15:42:42
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSelected {

    private String label;
    private Object value;

}