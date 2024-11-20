import java.util.*;

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
        StringJoiner joiner = new StringJoiner(LINE_BREAK);
        for (WordFrequency wordFrequency : wordFrequencies) {
            String s = wordFrequency.getValue() + SPACE + wordFrequency.getWordCount();
            joiner.add(s);
        }
        return joiner.toString();
    }

    private List<WordFrequency> getProcessedWordFrequencies(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> wordToWordFrequencies = getGroupedWordFrequencies(wordFrequencies);

        List<WordFrequency> processedWordFrequencies = new ArrayList<>();
        for (Map.Entry<String, List<WordFrequency>> entry : wordToWordFrequencies.entrySet()) {
            WordFrequency wordFrequency = new WordFrequency(entry.getKey(), entry.getValue().size());
            processedWordFrequencies.add(wordFrequency);
        }
        wordFrequencies = processedWordFrequencies;

        wordFrequencies.sort((wordFrequency, nextWordFrequency) -> nextWordFrequency.getWordCount() - wordFrequency.getWordCount());
        return wordFrequencies;
    }

    private List<WordFrequency> getWordFrequencies(String sentence) {
        String[] words = sentence.split(SPACE_REGEX);

        List<WordFrequency> wordFrequencies = new ArrayList<>();
        for (String word : words) {
            WordFrequency wordFrequency = new WordFrequency(word, 1);
            wordFrequencies.add(wordFrequency);
        }
        return wordFrequencies;
    }

    private Map<String, List<WordFrequency>> getGroupedWordFrequencies(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> groupedWordFrequencies = new HashMap<>();
        for (WordFrequency wordFrequency : wordFrequencies) {
//       groupedWordFrequencies.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input);
            if (!groupedWordFrequencies.containsKey(wordFrequency.getValue())) {
                ArrayList classifiedWordFrequencies = new ArrayList<>();
                classifiedWordFrequencies.add(wordFrequency);
                groupedWordFrequencies.put(wordFrequency.getValue(), classifiedWordFrequencies);
            } else {
                groupedWordFrequencies.get(wordFrequency.getValue()).add(wordFrequency);
            }
        }
        return groupedWordFrequencies;
    }
}
