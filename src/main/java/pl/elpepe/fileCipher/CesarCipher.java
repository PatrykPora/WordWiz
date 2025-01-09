package pl.elpepe.fileCipher;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class CesarCipher implements FileCipher {

    @Override
    public String encrypt(String text) {
        StringBuilder shifted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            shifted.append((char) (ch + 1));
        }
        return shifted.toString();
    }

    @Override
    public String decrypt(String text) {
        StringBuilder shifted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            shifted.append((char) (ch - 1));
        }
        return shifted.toString();
    }
}
