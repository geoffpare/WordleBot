package bots;

import com.google.common.collect.Sets;
import game.Board;
import game.BoardKnowledge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HardModeBot {
    private final List<String> validWords;
    private List<String> remainingWords;
    private final String firstGuess = "split";

    public HardModeBot(List<String> validWords) {
        this.validWords = validWords;
        this.remainingWords = new ArrayList<>(validWords);
    }

    public void solve(Board board) {
        System.out.println("Starting board...");
        System.out.println("Guessing: " + firstGuess);
        BoardKnowledge bk = board.submitGuess(firstGuess);  // Hard code starting word for now
        System.out.println(bk);

        while (!board.isGameOver()) {
            String nextWord = nextBestWord(bk);
            System.out.println("Guessing: " + nextWord);
            bk = board.submitGuess(nextWord);
        }

        if (board.isBoardSolved()) {
            System.out.println("Solved game via " + bk.getGuesses() + " in " + bk.getGuesses().size());
        } else {
            System.out.println("Failed to solve board");
        }
    }

    // Follows hard mode rules that must pick a word based on the existing board knowledge
    public String nextBestWord(BoardKnowledge bk) {
        System.out.println("Filtering word list based on known letters, starting word list size: " + remainingWords.size());

        // Filter based on generic known letters
        List<String> newRemainingWords = new ArrayList<>();

        for (String word : remainingWords) {
            if (Sets.newHashSet(word.split("")).containsAll(bk.getWordContainsLetters())) {
                newRemainingWords.add(word);
            }
        }

        System.out.println("New remaining words list size: " + newRemainingWords.size());
        remainingWords = newRemainingWords;

        List<String> wordsMatchingPositions = new ArrayList<>();

        // Filter based on letter locations
        for (String word : newRemainingWords) {
            if (doLetterPositionsMatch(word, bk.getLetterAtPositionMustBe())) {
                wordsMatchingPositions.add(word);
            }
        }

        System.out.println("Remaining word list size based on positions: " + wordsMatchingPositions.size());
        remainingWords = wordsMatchingPositions;

        // Filter based on letters we know aren't present
        List<String> nonLetters = getNonPresentLetters(bk);
        System.out.println("Letters which aren't present in target: " + nonLetters);

        List<String> wordsNotMatchingKnownNonLetters = new ArrayList<>();
        for (String word : wordsMatchingPositions) {
            if (!wordContainsAnyLetter(word, nonLetters)) {
                wordsNotMatchingKnownNonLetters.add(word);
            }
        }

        System.out.println("Remaining word list size based on non-letters: " + wordsNotMatchingKnownNonLetters.size());
        remainingWords = wordsNotMatchingKnownNonLetters;

        // Deterministically just pick the first remaining valid word as our guess in this bot
        String guess = remainingWords.get(0);
        remainingWords.remove(guess);
        return guess;
    }

    private boolean wordContainsAnyLetter(String word, List<String> letters) {
        for (String letter : letters) {
            if (word.contains(letter)) {
                return true;
            }
        }

        return false;
    }

    private List<String> getNonPresentLetters(BoardKnowledge bk) {
        Set<String> guessedLetters = new HashSet<>();
        for (String word : bk.getGuesses()) {
            guessedLetters.addAll(List.of(word.split("")));
        }

        return new ArrayList(Sets.difference(guessedLetters, bk.getWordContainsLetters()));
    }

    private boolean doLetterPositionsMatch(String word, List<String> letterAtPosition) {
        List<String> checkWord = List.of(word.split(""));

        for (int i=0; i<word.length(); i++) {
            // Empty string means unknown
            if (letterAtPosition.get(i).equals("")) {
                continue;
            }

            if (!checkWord.get(i).equals(letterAtPosition.get(i))) {
                return false;
            }
        }

        return true;
    }
}
