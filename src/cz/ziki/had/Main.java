package cz.ziki.had;

public class Main {

    /**
     * The main method that starts the game by initializing the window and starting its thread.
     */
    public static void main(String[] args) {

        Window window = Window.getWindow();

        Thread thread = new Thread(window);
        thread.start();

    }
}