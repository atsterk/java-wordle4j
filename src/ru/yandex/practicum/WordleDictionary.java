package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;
    private final PrintWriter logFile;

    public WordleDictionary(List<String> words, PrintWriter logFile) {
        this.words = words;
        this.logFile = logFile;
        normalize();
    }

    public void normalize() {
        words.replaceAll(s -> s.toLowerCase().replace("ё", "e"));
    }

    public String getRandomWord() {
        return words.get(new Random().nextInt(words.size()));
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getGuessWord(LinkedHashMap<String, String> guessesMap) {
        if (guessesMap.isEmpty()) {
            return getRandomWord();
        }
        StringBuilder possibleWord = new StringBuilder("?????");
        Set<Character> possibleLetters = new HashSet<>();
        Set<Character> impossibleLetters = new HashSet<>();

        for (String guess : guessesMap.keySet()) {
            for (int i = 0; i < possibleWord.length(); i++) {
                char letterHint = guessesMap.get(guess).charAt(i);
                if (letterHint == '+') {
                    possibleWord.setCharAt(i, guess.charAt(i));
                } else if (letterHint == '^') {
                    possibleLetters.add(guess.charAt(i));
                } else {
                    impossibleLetters.add(guess.charAt(i));
                }
            }
        }

        for (String word : words) {
            boolean skip = guessesMap.containsKey(word);

            for (int i = 0; i < word.length(); i++) {
                if (possibleWord.charAt(i) != '?' && word.charAt(i) != possibleWord.charAt(i)) {
                    skip = true;
                }
            }
            for (char letter : possibleLetters) {
                if (word.indexOf(letter) == -1) {
                    skip = true;
                }
            }
            for (char letter : impossibleLetters) {
                if (word.indexOf(letter) != -1) {
                    skip = true;
                }
            }
            if (!skip) {
                return word;
            }
        }

        return "";
    }
}
