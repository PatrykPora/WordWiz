package pl.elpepe;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.*;

@Repository
public class TextTranslationRepository {

    private List<TextTranslation> textTranslations;

    public TextTranslationRepository(FileService fileService) {
        try {
            this.textTranslations = fileService.readAllFile();
        } catch (IOException e) {
            textTranslations = new ArrayList<TextTranslation>();
        }
    }

    public List<TextTranslation> getAllTextTranslations() {
        return textTranslations;
    }

    public void add(TextTranslation textTranslation) {
        textTranslations.add(textTranslation);
    }

    public int size() {
        return textTranslations.size();
    }

    public boolean isEmpty() {
        return textTranslations.isEmpty();
    }

    Set<TextTranslation> getRandomTextTranslations(int number) {
        Random random = new Random();
        Set<TextTranslation> randomTranslations = new HashSet<>();
        while (randomTranslations.size() < number && randomTranslations.size() < textTranslations.size()) {
            randomTranslations.add(textTranslations.get(random.nextInt(textTranslations.size())));
        }
        return randomTranslations;
    }


}
