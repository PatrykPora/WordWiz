package pl.elpepe;

import org.springframework.stereotype.Component;

@Component
public class UpperCaseTextFormatter implements TextFormatter {
    @Override
    public String formatText(String text) {
        return text.toUpperCase();
    }
}
