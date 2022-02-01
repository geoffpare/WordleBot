package game;

import com.google.common.collect.Sets;

import java.util.*;

public class Board {
    private static int MAX_GUESSES = 6;
    private static int WORD_LENGTH = 5;

    private String targetWord;
    private List<String> guesses = new ArrayList<>(5);

    public Board(String targetWord) {
        this.targetWord = targetWord;
    }

    // Returns what you now know after the guess
    public BoardKnowledge submitGuess(String word) {
        if (guesses.size() >= MAX_GUESSES) {
            throw new RuntimeException("Your bot made too many guesses");
        }

        guesses.add(word);

        return generateBoardKnowledge();
    }

    public BoardKnowledge generateBoardKnowledge() {
        Set<String> wordContainsLetters = getWordContainsLetters();
        List<String> letterAtPositionMustBe = getLetterAtPositions();

        return new BoardKnowledge(wordContainsLetters, letterAtPositionMustBe, guesses);
    }

    private List<String> getLetterAtPositions() {
        List<String> letterAtPosition = new ArrayList<>(Arrays.asList("", "", "", "", ""));

        for (String guess : guesses) {
            for (int i=0; i<WORD_LENGTH; i++){
                if (guess.charAt(i) == targetWord.charAt(i)) {
                    letterAtPosition.set(i, String.valueOf(guess.charAt(i)));
                }
            }
        }

        return letterAtPosition;
    }

    // The intersection of the letters in targetWord with all letters from all guesses
    private Set<String> getWordContainsLetters() {
        Set<String> wordContainsLetters = new HashSet<>();
        wordContainsLetters.addAll(List.of(targetWord.split("")));

        Set<String> allGuessedLetters = new HashSet<>();

        for (String word : guesses) {
            allGuessedLetters.addAll(List.of(word.split("")));
        }

        return Sets.intersection(wordContainsLetters, allGuessedLetters);
    }

    public boolean isBoardSolved() {
        return guesses.contains(targetWord);
    }

    public boolean isGameOver() {
        return guesses.size() >= MAX_GUESSES || isBoardSolved();
    }
}
