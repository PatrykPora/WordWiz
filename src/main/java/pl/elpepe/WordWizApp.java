package pl.elpepe;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class WordWizApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(WordWizApp.class, args);
        WordWizController controller = appContext.getBean(WordWizController.class); // get controller object from container to run start method
        controller.startMainLoop();
    }

    @Bean
    Scanner scanner() {
        return new Scanner(System.in);
    }
}