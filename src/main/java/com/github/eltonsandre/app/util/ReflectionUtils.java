package com.github.eltonsandre.app.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author eltonsandre
 * @date 2 de nov de 2017 18:51:10
 */
@Slf4j
@UtilityClass
public class ReflectionUtils {

    /**
     * @param t T
     * @param methodName nome do metodo
     * @return Object
     */
    public <T, C> Object invokeMethod(final T t, final String methodName, final Class<C>[] clazzs, final Object... objects) {
        try {
            return t.getClass().getMethod(methodName, clazzs).invoke(t, objects);
        } catch (final Exception e) {
            log.error("status=ERROR, msg={}", e.getMessage());
        }
        return null;
    }

    /**
     * @param t T
     * @param methodName nome do metodo
     * @return Object
     */
    public <T> Object invokeMethod(final T t, final String methodName) {
        try {
            return t.getClass().getMethod(methodName, new Class[] {}).invoke(t);
        } catch (final Exception e) {
            log.error("status=ERROR, msg={}", e.getMessage());
        }
        return null;
    }

    /**
     * @param t
     * @param method
     * @param args
     * @param <T>
     * @param <C>
     * @return
     */
    public <T, C> Object invokeMethod(final T t, final Method method, final Object... args) {
        try {
            return method.invoke(t, args);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz class
     * @param fildName nome do field
     * @return Field
     */
    public Field getField(final Class<?> clazz, final String fildName) {
        try {
            return clazz.getField(fildName);
        } catch (final Exception e) {
            log.error("status=ERROR, msg={}", e.getMessage());
        }
        return null;
    }

    /**
     * @param clazz class
     * @return List<Field>
     */
    public List<Field> getPrivateFields(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()))
                .collect(Collectors.toList());
    }

    /**
     * @param clazz class
     * @return List<Method>
     */
    public List<Method> getAccessibleMethods(final Class<?> clazz) {
        if (clazz == null) {
            return List.of();
        }
        return getSafeModifiersPublic(clazz);
    }

    /**
     * @param field class
     * @return List<Method>
     */
    public List<Method> getMethodsByField(@NonNull final Field field) {
        if (field == null) {
            return List.of();
        }
        return getSafeModifiersPublic(field.getType());
    }

    /**
     * @param type class
     * @return List<Method>
     */
    public List<Method> getSafeModifiersPublic(final Class<?> type) {
        try {
            return getModifiersPublic(type);
        } catch (final Exception e) {
            log.error("status=ERROR, msg={}", e.getMessage());
        }
        return List.of();
    }

    /**
     * @param type class
     * @return List<Method>
     */
    public List<Method> getModifiersPublic(final Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .collect(Collectors.toList());
    }

}
