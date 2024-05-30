import java.io.*;
import java.util.*;

public class FileUtils {

    private static final String FILENAME = "player_scores.txt";

    public static void savePlayerScore(String name, int score) {
        Map<String, Integer> scores = loadPlayerScores();
        scores.put(name, score);

        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        if (sortedScores.size() > 3) {
            sortedScores = sortedScores.subList(0, 3);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Map.Entry<String, Integer> entry : sortedScores) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> loadPlayerScores() {
        Map<String, Integer> scores = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
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
}