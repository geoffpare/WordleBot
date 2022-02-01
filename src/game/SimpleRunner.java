package game;

import bots.HardModeBot;
import utils.InputReader;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Simple main class for running bots against boards
 */
public class SimpleRunner {

    /*
     * Load dictionary
     * Create bot
     * Solve puzzles
     * Output stats
     */
    public static void main(String[] args) throws FileNotFoundException {
        InputReader reader = new InputReader();
        List<String> dict = reader.loadStringsFromFile("./data/wordlewords.txt");
        System.out.println("Dictionary size: " + dict.size());

        Board board = new Board("brick");
        //RandomGuessBot bot = new RandomGuessBot(dict);
        HardModeBot bot = new HardModeBot(dict);

        bot.solve(board);

    }
}
