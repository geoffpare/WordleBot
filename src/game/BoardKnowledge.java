package game;

import java.util.List;
import java.util.Set;

/**
 * Represents what the guesser has learned as for as letter positions
 */
public class BoardKnowledge {
    private Set<String> wordContainsLetters;
    private List<String> letterAtPositionMustBe;  // e.g. letter.at(1) must be "b"
    private List<String> guesses;

    public BoardKnowledge(Set<String> wordContainsLetters, List<String> letterAtPositionMustBe, List<String> guesses) {
        this.wordContainsLetters = wordContainsLetters;
        this.letterAtPositionMustBe = letterAtPositionMustBe;
        this.guesses = guesses;
    }

    public Set<String> getWordContainsLetters() {
        return wordContainsLetters;
    }

    public List<String> getLetterAtPositionMustBe() {
        return letterAtPositionMustBe;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word contains: " + wordContainsLetters.toString() + "\n");
        sb.append("Letters at Position: [" + letterAtPositionMustBe.toString() + "]");
        return sb.toString();
    }
}
