package ru.yandex.practicum;


import java.io.PrintWriter;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static final String LOG_FILE_NAME = "log.txt";
    private static final String PATH_TO_DICTIONARY = "words_ru.txt";
    public static void main(String[] args) {
        try (PrintWriter logFile = new PrintWriter(LOG_FILE_NAME)) {
            WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader(PATH_TO_DICTIONARY, logFile);
            WordleDictionary dictionary = dictionaryLoader.getDictionary();
            WordleGame gameInstance = new WordleGame(dictionary, logFile, dictionary.getRandomWord());

            System.out.println("Начало игры Wordle");
            Scanner scanner = new Scanner(System.in);

            while(gameInstance.ready()) {
                try {
                    System.out.println("Введите слово из 5 букв или нажмите Enter для получения подсказки");
                    String guess = scanner.nextLine().toLowerCase().replace("ё", "e");
                    if (guess.isEmpty()) {
                        guess = gameInstance.getGuess();
                        System.out.println(guess);
                    }
                    String hint = gameInstance.makeGuess(guess);
                    System.out.println(hint);
                } catch (GameException exp) {
                    System.out.println(exp.getMessage() + " Попробуйте снова");
                }
            }
            if (gameInstance.isGuessed()) {
                System.out.println("Поздравляю, вы угадали слово");
            } else {
                System.out.println("К сожалению, угадать слово не получилось");
            }
        } catch (Exception exp) {
            System.err.println("Критическая ошибка " + exp.getMessage());
        }
    }

}
