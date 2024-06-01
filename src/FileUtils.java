import java.io.*;
import java.net.URISyntaxException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Utility class for handling player score saving and loading operations.
 */
public class FileUtils {

    private static final String FILENAME = "player_scores.txt";

    /**
     * Saves a player's score to a file. If the file contains more than 3 scores,
     * it keeps only the top 3 highest scores.
     *
     * @param name  the name of the player
     * @param score the score of the player
     */
    public static void savePlayerScore(String name, int score) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        Map<String, Integer> scores = loadPlayerScores();
        scores.put(formattedDateTime + " " + name, score);

        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        if (sortedScores.size() > 3) {
            sortedScores = sortedScores.subList(0, 3);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for (Map.Entry<String, Integer> entry : sortedScores) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the player scores from a file.
     *
     * @return a map of player names and their scores
     */
    public static Map<String, Integer> loadPlayerScores() {
        Map<String, Integer> scores = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line == "") continue;
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.put(name, score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }

    /**
     * Gets the file path of the player scores file. If the file does not exist, it creates a new file.
     *
     * @return the absolute file path of the player scores file
     * @throws IOException if an I/O error occurs
     */
    private static String getFilePath() throws IOException {
        URL url = FileUtils.class.getProtectionDomain().getCodeSource().getLocation();
        if (url != null) {
            try {
                URI uri = new URI(URLEncoder.encode(url.toString(), "UTF-8"));
                String decoded = URLDecoder.decode(uri.toString());
                String finalString = decoded.substring(6);
                File file = new File(finalString);
                File scoreFile = new File(file.getParentFile().getAbsolutePath() + "/" + FILENAME);
                if (!scoreFile.exists()){
                    scoreFile.createNewFile();
                }
                return scoreFile.getAbsolutePath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("URL is null.");
        }
        return null;
    }
}