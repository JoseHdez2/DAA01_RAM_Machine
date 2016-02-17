package util;

public abstract class Sys {

    /**
     * Alias for outputting with a format.
     * @param str
     * @param objects
     */
    public static void out(String format, Object... objects){
        System.out.println(String.format(format, objects));
    }
}
