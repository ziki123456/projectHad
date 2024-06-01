/**
 * Utility class for managing time-related operations.
 */
public class Time {

    /**
     * Time when the application started in nanoseconds.
     */

    public static double timeStarted = System.nanoTime();

    /**
     * Gets the current time elapsed since the application started.
     * @return The elapsed time in seconds.
     */

    public static double getTime() {
        return (System.nanoTime() - timeStarted) * 1E-9;
    }

}
