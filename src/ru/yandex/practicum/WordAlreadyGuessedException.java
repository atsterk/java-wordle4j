package ru.yandex.practicum;

public class WordAlreadyGuessedException  extends GameException {
    public WordAlreadyGuessedException(String message) {
        super(message);
    }
}
