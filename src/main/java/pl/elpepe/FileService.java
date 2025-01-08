package pl.elpepe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {


    private final String FILE_NAME = "data.csv";

    List<TextTranslation> readAllFile() throws IOException {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            throw new IOException("file now found " + FILE_NAME);
        }
        try (var lines = Files.lines(Paths.get(FILE_NAME))) {
            return lines
                    .map(CsvConverter::parse)
                    .collect(Collectors.toList());
        }
    }

    void saveTextTranslation(List<TextTranslation> textTranslations) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (TextTranslation textTranslation : textTranslations) {
                bufferedWriter.write(textTranslation.toString());
                bufferedWriter.newLine();
            }
        }
    }

    private static class CsvConverter {
        static TextTranslation parse(String text) {
            String[] split = text.split(";");
            return new TextTranslation(split[0], split[1]);
        }
    }
}
