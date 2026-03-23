package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.LinkedHashMap;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private static final int NUMBER_OF_GUESSES = 6;

    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;
    private final PrintWriter logFile;
    LinkedHashMap<String, String> guessesMap;
    private boolean isGuessed;

    public WordleGame(WordleDictionary dictionary, PrintWriter logFile, String answer) {
        this.dictionary = dictionary;
        this.steps = NUMBER_OF_GUESSES;
        this.answer = answer;
        this.guessesMap = new LinkedHashMap<>();
        this.logFile = logFile;
        this.isGuessed = false;
    }

    public boolean ready() {
        return steps > 0 && !isGuessed;
    }

    public boolean isGuessed() {
        return isGuessed;
    }

    public String makeGuess(String guess)
            throws WordNotFoundInDictionaryException, WordLengthIsWrongException, WordAlreadyGuessedException {
        StringBuilder hint = new StringBuilder();
        if (guess.equals(answer)) {
            this.isGuessed = true;
            return "+++++";
        }
        if (guess.length() != 5) {
            throw new WordLengthIsWrongException("Длина введенного слова не подходит.");
        } else if (!dictionary.contains(guess)) {
            throw new WordNotFoundInDictionaryException("Такого слова не существует.");
        } else if (guessesMap.containsKey(guess)) {
            throw new WordAlreadyGuessedException("Вы уже вводили это слово.");
        } else {
            for (int i = 0; i < 5; i++) {
                if (guess.charAt(i) == answer.charAt(i)) {
                    hint.append("+");
                } else if (answer.indexOf(guess.charAt(i)) != -1) {
                    hint.append("^");
                } else {
                    hint.append("-");
                }
            }
        }
        guessesMap.put(guess, hint.toString());
        steps--;
        hint.append(" Осталось попыток - ").append(steps);

        return hint.toString();
    }

    public String getGuess() {
        return dictionary.getGuessWord(guessesMap);
    }
}
