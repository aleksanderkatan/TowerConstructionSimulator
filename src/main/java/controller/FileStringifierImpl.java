package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileStringifierImpl implements FileStringifier {
    public String stringifyFile(String path) {
        File file = new File(path);
        StringBuilder answer = new StringBuilder();
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            answer.append(scanner.nextLine());
            answer.append("\n");
        }
        scanner.close();
        return new String(answer);
    }
}
