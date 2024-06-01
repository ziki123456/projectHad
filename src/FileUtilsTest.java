import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.*;
import java.io.*;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    public void testScoreFileExists() throws IOException {

        String fileName = "player_scores.txt";
        URL url = FileUtils.class.getProtectionDomain().getCodeSource().getLocation();
        if (url != null) {
            try {
                URI uri = new URI(URLEncoder.encode(url.toString(), "UTF-8"));
                String decoded = URLDecoder.decode(uri.toString());
                String finalString = decoded.substring(6);
                File file = new File(finalString);
                File scoreFile = new File(file.getParentFile().getAbsolutePath() + "/" + fileName);
                String currentDirectory = System.getProperty("user.dir");

                assertTrue(scoreFile.exists(), "File " + fileName + " should exist in the current directory. current directory: " + currentDirectory);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}