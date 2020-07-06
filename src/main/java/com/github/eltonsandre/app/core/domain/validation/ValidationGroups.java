package com.github.eltonsandre.app.core.domain.validation;

/**
 * @author eltonsandre
 * date 03/05/2020 18:13
 */
public interface ValidationGroups {
    static Class<?>[] all() {
        return new Class[] { ValidationGroups.Insert.class, ValidationGroups.Upadate.class, ValidationGroups.Delete.class };
    }

    static Class<?>[] save() {
        return new Class[] { ValidationGroups.Insert.class, ValidationGroups.Upadate.class };
    }

    public interface Insert {
    }

    public interface Upadate {
    }

    public interface Delete {
    }
}
