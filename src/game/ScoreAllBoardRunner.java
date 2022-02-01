package game;

import bots.HardModeBot;
import utils.InputReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Runner which will run a single bot against all possible boards then output the final bot score
 */
public class ScoreAllBoardRunner {
    /*
     * Load dictionary
     * Create bot
     * Iterate over all boards adding up the scores
     * Output stats
     */
    public static void main(String[] args) throws FileNotFoundException {
        InputReader reader = new InputReader();
        List<String> dict = reader.loadStringsFromFile("./data/wordlewords.txt");
        System.out.println("Dictionary size: " + dict.size());

        List<Board> results = new ArrayList<>();

        for (String word : dict) {
            Board board = new Board(word);
            HardModeBot bot = new HardModeBot(dict);
            results.add(bot.solve(board));
        }

        System.out.println("Final score for Hard Mode bot: " + getFinalScore(results));
        System.out.println(formatDistribution(getGuessDistribution(results), results.size()));

    }

    private static int getFinalScore(List<Board> resultBoards) {
        return resultBoards.stream().collect(Collectors.summingInt(Board::getBoardScore));
    }

    private static String formatDistribution(Map<Integer, Integer> dist, Integer numBoards) {
        StringBuilder sb = new StringBuilder();

        sb.append("Distribution over " + numBoards + " games:\n");
        sb.append(String.format("1 : %4d (%.2f)%%\n", dist.get(1), (float)100*dist.get(1)/numBoards));
        sb.append(String.format("2 : %4d (%.2f)%%\n", dist.get(2), (float)100*dist.get(2)/numBoards));
        sb.append(String.format("3 : %4d (%.2f)%%\n", dist.get(3), (float)100*dist.get(3)/numBoards));
        sb.append(String.format("4 : %4d (%.2f)%%\n", dist.get(4), (float)100*dist.get(4)/numBoards));
        sb.append(String.format("5 : %4d (%.2f)%%\n", dist.get(5), (float)100*dist.get(5)/numBoards));
        sb.append(String.format("6 : %4d (%.2f)%%\n", dist.get(6), (float)100*dist.get(6)/numBoards));
        sb.append(String.format("X : %4d (%.2f)%%\n", dist.get(0), (float)100*dist.get(0)/numBoards));

        return sb.toString();
    }

    // Guess distribution, not point distribution
    private static Map<Integer, Integer> getGuessDistribution(List<Board> resultBoards) {
        Map<Integer, Integer> dist = new HashMap<>() {{
            put(1, 0);
            put(2, 0);
            put(3, 0);
            put(4, 0);
            put(5, 0);
            put(6, 0);
            put(0, 0); // Failed to solve
        }};

        for (Board board : resultBoards) {
            if (board.isBoardSolved()) {
                dist.put(board.getGuesses().size(), dist.get(board.getGuesses().size()) + 1);
            } else {
                dist.put(0, dist.get(0)+1);
            }
        }

        return dist;
    }
}
