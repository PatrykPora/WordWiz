package pl.elpepe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

@Controller
public class WordWizController {
    public static final int UNDEFINED = -1;
    public static final int ADD_WORD = 0;
    public static final int LEARN = 1;
    public static final int CLOSE_APP = 2;

    private final TextTranslationRepository repository;
    private final FileService fileService;
    private final Scanner scanner;
    private final ConsoleTextWriter consoleTextWriter;

    @Autowired
    public WordWizController(TextTranslationRepository repository, FileService fileService, Scanner scanner, ConsoleTextWriter consoleTextWriter) {
        this.repository = repository;
        this.fileService = fileService;
        this.scanner = scanner;
        this.consoleTextWriter = consoleTextWriter;
    }


    public void startMainLoop() {
        consoleTextWriter.print("Welcome User");
        int option = UNDEFINED;
        while (option != CLOSE_APP) {
            printMenu();
            option = chooseOption();
            executeOption(option);
        }
    }


    private void executeOption(int option) {
        switch (option) {
            case ADD_WORD -> addWord();
            case LEARN -> learn();
            case CLOSE_APP -> closeApp();
            default -> consoleTextWriter.print("Invalid option");
        }
    }

    private void learn() {
        if (repository.isEmpty()) {
            consoleTextWriter.print("base is empty, add at least one word");
            return;
        }

        final int testSize = Math.min(repository.size(), 10);
        Set<TextTranslation> textTranslations = repository.getRandomTextTranslations(testSize);
        int score = 0;
        for (TextTranslation textTranslation : textTranslations) {
            consoleTextWriter.print("enter translation for : " + textTranslation.getOriginal());
            String translation = scanner.nextLine();
            if (textTranslation.getTranslation().equalsIgnoreCase(translation)) {
                consoleTextWriter.print("correct answer");
                score++;
            } else {
                consoleTextWriter.print("wrong answer - " + textTranslation.getTranslation());
            }
            consoleTextWriter.print("your score: " + score + "/" + testSize);
        }


    }

    private void closeApp() {
        try {
            fileService.saveTextTranslation(repository.getAllTextTranslations());
            consoleTextWriter.print("changes saved");
        } catch (IOException e) {
            consoleTextWriter.print("failed to save");
        }
    }

    private void addWord() {
        consoleTextWriter.print("Enter original word");
        String originalWord = scanner.nextLine();
        consoleTextWriter.print("enter translation");
        String translation = scanner.nextLine();
        TextTranslation textTranslation = new TextTranslation(originalWord, translation);
        repository.add(textTranslation);
    }

    private int chooseOption() {
        int option;
        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            option = UNDEFINED;
        } finally {
            scanner.nextLine();
        }
        if (option > UNDEFINED && option <= CLOSE_APP)
            return option;
        else
            return UNDEFINED;
    }


    private void printMenu() {
        consoleTextWriter.print("please choose an option");
        consoleTextWriter.print("0 - Add word");
        consoleTextWriter.print("1 - Learn");
        consoleTextWriter.print("2 - Close app");
    }
}
