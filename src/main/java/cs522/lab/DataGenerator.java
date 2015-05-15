package cs522.lab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by tilak on 5/12/15.
 */
public class DataGenerator {

    private static final int ROWS = 10;

    public static void main(String[] args) throws IOException {
        generateRandomDataFile("./input/events01.txt");
        generateRandomDataFile("./input/events02.txt");
        generateRandomDataFile("./input/events03.txt");
    }

    static void writeToFile(String file, StringBuilder sb) throws IOException {
        Files.write(Paths.get(file), sb.toString().getBytes());
    }

    static void generateRandomDataFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < randBetween(5, 10); j++) {
                sb.append(randBetween(25, 50) + " ");
            }
            line.append(System.lineSeparator());
            sb.append(line);
        }
        writeToFile(fileName, sb);
    }

    private static int randBetween(int low, int high) {
        return (int) (Math.random() * (high - low)) + low;
    }
}
