public class Main {
    public static void main(String[] args) {

        /**
         * The main method that starts the game by initializing the window and starting its thread.
         */

        Window window = Window.getWindow();

        Thread thread = new Thread(window);
        thread.start();

    }
}