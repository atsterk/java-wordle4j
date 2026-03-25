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
    public final PrintWriter logFile;

    public WordleDictionary(List<String> words, PrintWriter logFile) {
        this.words = words;
        this.logFile = logFile;
    }

    public String getRandomWord() {
        return words.get(new Random().nextInt(words.size()));
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getGuessWord(Map<String, String> guessesMap) {
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

        //цикл для прохода по всем словам словаря и проверки подходит ли каждое как подсказка
        outerloop:
        for (String word : words) {

            //проверка угадывалось ли слово ранее
            if (guessesMap.containsKey(word)) {
                continue;
            }

            //цикл для проверки имеются ли все уже известные по предыдущим подсказкам буквы "+" в слове
            for (int i = 0; i < word.length(); i++) {
                if (possibleWord.charAt(i) != '?' && word.charAt(i) != possibleWord.charAt(i)) {
                    continue outerloop; //если слово не подходит переходим к следующему слову
                }
            }

            //цикл для проверки имеются ли все уже известные по предыдущим подсказкам буквы "^" в слове
            for (char letter : possibleLetters) {
                if (word.indexOf(letter) == -1) {
                    continue outerloop; //если слово не подходит переходим к следующему слову
                }
            }

            //цикл для проверки что в слове нет букв "-"
            for (char letter : impossibleLetters) {
                if (word.indexOf(letter) != -1) {
                    continue outerloop; //если слово не подходит переходим к следующему слову
                }
            }

            //если во всех циклах все проверки прошли, то слово подходит как подсказка
            return word;

        }

        // Заменил return "" потому что такого не должно происходить, поэтому вместо этого в лог записываю ошибку
        // и выкидываю исключение, которое дальше в мейне отловится
        logFile.println("Поиск подсказки от компьютера не нашел подходящего слова в словаре.");
        throw new RuntimeException();
    }
}
