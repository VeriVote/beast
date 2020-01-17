package edu.pse.beast.saverloader.adapter;

import java.lang.reflect.Field;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * The Class SuperclassExclusionStrategy.
 *
 * @author Adrian Lee, Stackoverflow
 *         https://stackoverflow.com/a/20117863/5853965
 */
public final class SuperclassExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(final FieldAttributes fieldAttributes) {
        String fieldName = fieldAttributes.getName();
        Class<?> theClass = fieldAttributes.getDeclaringClass();

        return isFieldInSuperclass(theClass, fieldName);
    }

    /**
     * Checks if is field in superclass.
     *
     * @param subclass
     *            the subclass
     * @param fieldName
     *            the field name
     * @return true, if is field in superclass
     */
    private boolean isFieldInSuperclass(final Class<?> subclass,
                                        final String fieldName) {
        Class<?> superclass = subclass.getSuperclass();
        Field field;

        while (superclass != null) {
            field = getField(superclass, fieldName);
            if (field != null) {
                return true;
            }
            superclass = superclass.getSuperclass();
        }
        return false;
    }

    /**
     * Gets the field.
     *
     * @param theClass
     *            the the class
     * @param fieldName
     *            the field name
     * @return the field
     */
    private Field getField(final Class<?> theClass, final String fieldName) {
        try {
            return theClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException | SecurityException e) {
            return null;
        }
    }

    @Override
    public boolean shouldSkipClass(final Class<?> clazz) {
        return false;
    }
}
