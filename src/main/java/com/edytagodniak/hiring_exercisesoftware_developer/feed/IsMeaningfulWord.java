package com.edytagodniak.hiring_exercisesoftware_developer.feed;

import lombok.experimental.UtilityClass;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
class IsMeaningfulWord {

    //TODO could be done better - this is very primitive way of extracting words without meaning (top 100 english words)
    static final LinkedHashSet<String> WORDS_WITHOUT_MEANING = Stream.of(
            "a", "an", "about", "after", "all", "also", "am", "and", "are", "as", "at", "be", "because", "but", "by", "can",
            "come", "could", "day", "do", "even", "find", "first", "for", "from", "get", "give", "go",
            "have", "he", "her", "here", "him", "his", "how", "i", "if", "in", "into", "it", "its", "is",
            "just", "know", "like", "look", "make", "man", "many", "me", "more", "my", "new", "no",
            "not", "now", "of", "on", "one", "only", "or", "other", "our", "out", "over", "people", "say",
            "see", "she", "so", "some", "take", "tell", "than", "that", "the", "their", "them", "then",
            "there", "these", "they", "thing", "think", "this", "those", "time", "to", "two", "up",
            "use", "very", "want", "way", "we", "well", "what", "when", "which", "who", "why", "will", "with",
            "would", "year", "you", "your", "-")
            .collect(Collectors.toCollection(LinkedHashSet::new));

    boolean test(String s) {
        return !WORDS_WITHOUT_MEANING.contains(s);
    }

}
