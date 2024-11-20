import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {
    public static final String SPACE_REGEX = "\\s+";
    public static final String LINE_BREAK = "\n";
    public static final String CALCULATE_ERROR = "Calculate Error";
    public static final String SPACE = " ";

    public String getWordFrequency(String sentence) {
        if (sentence.split(SPACE_REGEX).length == 1) {
            return sentence + " 1";
        } else {
            try {
                List<WordFrequency> wordFrequencies = getWordFrequencies(sentence);

                wordFrequencies = getProcessedWordFrequencies(wordFrequencies);

                return getFormattedWordFrequencies(wordFrequencies);
            } catch (Exception e) {
                return CALCULATE_ERROR + e;
            }
        }
    }

    private String getFormattedWordFrequencies(List<WordFrequency> wordFrequencies) {
        return wordFrequencies.stream()
                .map(w -> w.getValue() + SPACE + w.getWordCount())
                .collect(Collectors.joining(LINE_BREAK));
    }

    private List<WordFrequency> getProcessedWordFrequencies(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> wordToWordFrequencies = getGroupedWordFrequencies(wordFrequencies);

        return wordToWordFrequencies.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .sorted((wordFrequency, nextWordFrequency) -> nextWordFrequency.getWordCount() - wordFrequency.getWordCount())
                .collect(Collectors.toList());
    }

    private List<WordFrequency> getWordFrequencies(String sentence) {
        String[] words = sentence.split(SPACE_REGEX);

        return Arrays.stream(words)
                .map(word -> new WordFrequency(word, 1))
                .collect(Collectors.toList());
    }

    private Map<String, List<WordFrequency>> getGroupedWordFrequencies(List<WordFrequency> wordFrequencies) {
        return wordFrequencies.stream()
                .collect(Collectors.groupingBy(WordFrequency::getValue));
    }
}
