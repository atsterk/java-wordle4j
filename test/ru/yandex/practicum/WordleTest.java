package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    private static WordleGame wordleGame;

    @BeforeEach
    void doSomething() {
        PrintWriter logFile = new PrintWriter(System.out);
        List<String> words = new ArrayList<>();
        words.add("трава");
        words.add("бочка");
        words.add("ведро");
        WordleDictionary dictionary = new WordleDictionary(words, logFile);
        wordleGame = new WordleGame(dictionary, logFile, "трава");
    }

    @Test
    void testWordleGameThrowsExceptions() throws WordAlreadyGuessedException, WordNotFoundInDictionaryException, WordLengthIsWrongException {

        Assertions.assertThrows(WordNotFoundInDictionaryException.class, () -> wordleGame.makeGuess("11111"));
        Assertions.assertThrows(WordLengthIsWrongException.class, () -> wordleGame.makeGuess("кот"));
        wordleGame.makeGuess("ведро");
        Assertions.assertThrows(WordAlreadyGuessedException.class, () -> wordleGame.makeGuess("ведро"));
    }

    @Test
    void testWordleGameMakeGuess() throws WordAlreadyGuessedException, WordNotFoundInDictionaryException, WordLengthIsWrongException {
        assertEquals("^--^- Осталось попыток - 5", wordleGame.makeGuess("ведро"));
        assertEquals("----+ Осталось попыток - 4", wordleGame.makeGuess("бочка"));
        assertEquals("+++++", wordleGame.makeGuess("трава"));
    }
}
