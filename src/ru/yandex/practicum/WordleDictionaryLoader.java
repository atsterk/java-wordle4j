package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private static final int WORD_LENGTH = 5;
    private final String path;
    private final PrintWriter logFile;

    public WordleDictionaryLoader(String path, PrintWriter logFile) {
        this.path = path;
        this.logFile = logFile;
    }

    public WordleDictionary getDictionary() throws IOException {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            while (br.ready()) {
                String word = br.readLine();
                if (word.length() == WORD_LENGTH) {
                    words.add(word);
                }
            }
            return new WordleDictionary(words, logFile);
        } catch (IOException exp) {
            logFile.println(exp.getMessage());
            throw exp;
        }
    }
}
