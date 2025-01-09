package pl.elpepe.fileCipher;

import org.springframework.stereotype.Component;

@Component
public class DictionaryFileCipher {

    private final FileCipher fileCipher;

    public DictionaryFileCipher(FileCipher fileCipher) {
        this.fileCipher = fileCipher;
    }

    public String encryptFile(String input) {
        return fileCipher.encrypt(input);
    }

    public String decryptFile(String input) {
        return fileCipher.decrypt(input);
    }
}
