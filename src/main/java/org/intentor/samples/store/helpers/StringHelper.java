package org.intentor.samples.store.helpers;

/**
 * Helper methods for string manipulation/checking.
 */
public final class StringHelper {
    /**
     * Evaluates whether a string is empty.
     *
     * @param str String to be evaluated.
     * @return True if empty, otherwise false.
     */
    public static boolean notEmpty(final String str) {
        return str != null && !str.trim().isEmpty();
    }
}
