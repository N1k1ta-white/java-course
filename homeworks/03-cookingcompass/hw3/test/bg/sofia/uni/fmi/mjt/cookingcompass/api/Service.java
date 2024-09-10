package bg.sofia.uni.fmi.mjt.cookingcompass.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public class Service {
    private static Path path;
    private static Path path2;

    static {
        path = Path.of("json.txt");
        path2 = Path.of("json2.txt");
    }

    public static String getJson() {
        StringBuilder res = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                res.append(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Something going wrong with file", e);
        }
        return res.toString();
    }

    public static String getJson2() {
        StringBuilder res = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path2.toFile()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                res.append(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Something going wrong with file", e);
        }
        return res.toString();
    }
}
