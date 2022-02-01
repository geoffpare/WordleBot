package bots;

import game.Board;
import game.BoardKnowledge;

import java.util.List;
import java.util.Random;

/**
 * Random guess bot does what it says
 * Simple bot to test out the game
 */
public class RandomGuessBot {
    private static final int NUM_GUESSES = 6;
    private List<String> validWords;

    public RandomGuessBot(List<String> validWords) {
        this.validWords = validWords;
    }

    public void solve(Board board) {
        Random rand = new Random();

        System.out.println("Starting board...");

        for (int i=0; i<NUM_GUESSES; i++) {
            String guess = validWords.get(rand.nextInt(validWords.size()));
            System.out.println("Guessing: " + guess);
            BoardKnowledge bk = board.submitGuess(guess);
            System.out.println(bk);

            if (board.isBoardSolved()) {
                System.out.println("Board solved!");
                break;
            }

            if (board.isGameOver()) {
                System.out.println("Game over, guessing bot not good at this");
                break;
            }
        }

    }
}
