package pl.elpepe;

import org.springframework.stereotype.Component;

@Component
public class StandardTextFormater implements TextFormatter{
    @Override
    public String formatText(String text) {
        return text;
    }
}
