package ca.jent.demo.utils;

import java.util.function.Function;

public class Util {

    // only available within Util
    private interface OrDefault<T> {
        public T orElse(T t);
    }

    /**
     * String represent a very large set of possible values e.g. "abc", "1", "2.0", "", "0.9".
     * The purpose of this function is to reduce the possible string values that can be converted to an integer.
     * For instance, a value of "abc", "1R7" or "", shall return a default Integer since it cannot be converted.
     * Values that could be converted are string representation of "0", "0.9", "1", "2.3", "3.1777" and so forth.
     * All possible string representing an integer or decimal number value should be rounded to nearest Integer.
     * This method also restrict the conversion to positive Integer (including zero).  Hence: [0..MAX_INTEGER]
     * Usage:
     * Integer i = Util.mapToPositiveInteger("2.7").orElse(null); // 3
     * Integer i = Util.mapToPositiveInteger("abc").orElse(0);    // 0
     * @param value String
     * @return Anonymous Instance of type OrDefault<Integer>
     */
    public static OrDefault<Integer> mapToPositiveInteger(String value) {
        return defaultValue -> {
            if (value == null) return defaultValue;
            try {
                int i = Math.round(Float.valueOf(value));
                return i >= 0 ? i : defaultValue;
            } catch (NumberFormatException ignored) {}
            return defaultValue;
        };
    }

    /**
     * If we return a Function<Integer, Integer> then the default method name is `apply`.  Sometimes it is acceptable.
     * But other time we would prefer to re-name this method to something more appropriate, like `orElse`.
     * See the difference with this method and the one above.
     * Usage:
     * Integer i = Util.map("-3.9").apply(0);   // -4
     * Integer i = Util.map("abc").apply(null)  // null
     * As you can see, a better abstraction for `apply` would be `orElse` hence why it can be advantageous to declare
     * our own Functional interface as the method above.
     * @param value String
     * @return Function type of (Integer) -> Integer
     */
    public static Function<Integer, Integer> map(String value) {
        return defaultValue -> {
            if (value == null) return defaultValue;
            try {
                return Math.round(Float.valueOf(value));
            } catch (NumberFormatException ignored) {}
            return defaultValue;
        };
    }
}
