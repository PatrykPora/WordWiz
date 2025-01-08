package pl.elpepe;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class WordWizManager {
    public static final int UNDEFINED = -1;
    public static final int ADD_WORD = 0;
    public static final int LEARN = 1;
    public static final int CLOSE_APP = 2;

    private final TextTranslationRepository repository = new TextTranslationRepository();
    private final FileService fileService = new FileService();
    private final Scanner scanner = new Scanner(System.in);

    public void mainMenuLoop() {
        System.out.print("Welcome User");
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
            default -> System.out.println("Invalid option");
        }
    }

    private void learn() {
        if (repository.isEmpty()) {
            System.out.println("base is empty, add at least one word");
            return;
        }

        final int testSize = Math.min(repository.size(), 10);
        Set<TextTranslation> textTranslations = repository.getRandomTextTranslations(testSize);
        int score = 0;
        for (TextTranslation textTranslation : textTranslations) {
            System.out.printf("enter translation for :\"%s\"\n", textTranslation.getOriginal());
            String translation = scanner.nextLine();
            if (textTranslation.getTranslation().equalsIgnoreCase(translation)) {
                System.out.println("correct answer");
                score++;
            } else {
                System.out.println("wrong answer - " + textTranslation.getTranslation());
            }
            System.out.printf("your score: %d/%d\n", score, testSize);
        }


    }

    private void closeApp() {
        try {
            fileService.saveTextTranslation(repository.getAllTextTranslations());
            System.out.println("changes saved");
        } catch (IOException e) {
            System.out.print("failed to save");
        }
    }

    private void addWord() {
        System.out.println("Enter original word");
        String originalWord = scanner.nextLine();
        System.out.println("enter translation");
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
        System.out.println("please choose an option");
        System.out.println("0 - Add word");
        System.out.println("1 - Learn");
        System.out.println("2 - Close app");
    }
}
