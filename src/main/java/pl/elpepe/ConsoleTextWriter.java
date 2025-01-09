package pl.elpepe;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConsoleTextWriter {

    private final TextFormatter textFormatter;

    public ConsoleTextWriter(@Qualifier("standardTextFormater") TextFormatter textFormatter) {
        this.textFormatter = textFormatter;
    }

    void print(String text) {
        System.out.println(textFormatter.formatText(text));
    }
}
