package bots;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents what the guesser has learned as for as letter positions
 */
public class BoardKnowledge {
    private Set<String> wordContainsLetters;
    private List<String> letterAtPositionMustBe;  // e.g. letter.at(1) must be "b"

    public BoardKnowledge(Set<String> wordContainsLetters, List<String> letterAtPositionMustBe) {
        this.wordContainsLetters = wordContainsLetters;
        this.letterAtPositionMustBe = letterAtPositionMustBe;
    }

    public Set<String> getWordContainsLetters() {
        return wordContainsLetters;
    }

    public List<String> getLetterAtPositionMustBe() {
        return letterAtPositionMustBe;
    }
}
