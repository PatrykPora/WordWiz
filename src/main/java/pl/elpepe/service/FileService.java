package pl.elpepe.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.elpepe.fileCipher.FileCipher;
import pl.elpepe.repository.TextTranslation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FileService {

    private final String fileName;
    private final FileCipher fileCipher;

    public FileService(@Value("${dictionary.file}") String fileName, FileCipher fileCipher) {
        this.fileName = fileName;
        this.fileCipher = fileCipher;
    }


    public List<TextTranslation> readAllFile() throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            throw new IOException("file now found " + fileName);
        }
        try (var lines = Files.lines(Paths.get(fileName))) {
            return lines
                    .map(fileCipher::decrypt)
                    .map(CsvConverter::parse)
                    .collect(Collectors.toList());
        }
    }

    public void saveTextTranslation(List<TextTranslation> textTranslations) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (TextTranslation textTranslation : textTranslations) {
                bufferedWriter.write(fileCipher.encrypt(textTranslation.toString()));
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
