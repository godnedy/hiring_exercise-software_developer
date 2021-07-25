package com.edytagodniak.hiring_exercisesoftware_developer.feed;

import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedUrl;
import com.edytagodniak.hiring_exercisesoftware_developer.boundry.FeedItemProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FeedProcessingService {

    private static final Integer MAX_WORDS = 3;

    private final FeedItemProvider feedItemProvider;


    public Map<String, WordFrequencyAnalysisResultFullItemInfo> analyzeFeeds(List<FeedUrl> urls) {
        Stream<FeedItem> feedItemStream = feedItemProvider.processFeedsFromUrls(urls);
        return analyzeFeedsInMemory(feedItemStream);
    }

    private Map<String, WordFrequencyAnalysisResultFullItemInfo> analyzeFeedsInMemory(Stream<FeedItem> feedItems) {

        //copy of FeedId containing all info changed titles
        Map<UUID, FeedItem> notChangedFeeds = new HashMap<>();
        Map<String, TemporaryAnalysisResult> frequencyAnalysisResultMap = new HashMap<>();

        feedItems.peek(feedItem -> notChangedFeeds.put(feedItem.itemId, feedItem))
                .map(NoDuplicatesFeedItemCutInfo::of)
                .forEach(ndpf -> {
                    ndpf.noDuplicatesTitleWords.forEach( word ->
                    {
                        if (IsMeaningfulWord.test(word)) {
                            TemporaryAnalysisResult wordFrequencyAnalysisResult = frequencyAnalysisResultMap.get(word);
                            if (wordFrequencyAnalysisResult != null) {
                                List<UUID> oneFeedTitleIds = wordFrequencyAnalysisResult.itemIdsPerFeedId.get(ndpf.feedId);
                                if (oneFeedTitleIds == null) {
                                    wordFrequencyAnalysisResult.itemIdsPerFeedId.put(ndpf.feedId, new ArrayList<>(Arrays.asList(ndpf.itemId)));
                                    wordFrequencyAnalysisResult.increaseFrequency();
                                } else {
                                    oneFeedTitleIds.add(ndpf.itemId);
                                }
                            } else {
                                Map<Integer, List<UUID>> newItemsPerFeed = new HashMap<>();
                                newItemsPerFeed.put(ndpf.feedId, new ArrayList<>(Arrays.asList(ndpf.itemId)));
                                TemporaryAnalysisResult newWordResult = new TemporaryAnalysisResult(1, newItemsPerFeed);
                                frequencyAnalysisResultMap.put(word, newWordResult);
                            }
                        }
                    });
                });

        Map<String, TemporaryAnalysisResult> wordFrequencyAnalysisResultMap = threeMostFrequentWords(frequencyAnalysisResultMap);
        return finalResults(wordFrequencyAnalysisResultMap, notChangedFeeds);

    }

    private Map<String, TemporaryAnalysisResult> threeMostFrequentWords(Map<String, TemporaryAnalysisResult> resultMap) {
        return resultMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparingInt((TemporaryAnalysisResult wr) -> wr.frequency)
                        .reversed()))
                .limit(MAX_WORDS)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private Map<String, WordFrequencyAnalysisResultFullItemInfo> finalResults(Map<String, TemporaryAnalysisResult> results,
                                                                 Map<UUID, FeedItem> notChangedFeeds) {

        Map<String, WordFrequencyAnalysisResultFullItemInfo> fullResultsByWord = new HashMap<>();

        for (String word: results.keySet()) {

            TemporaryAnalysisResult temporaryAnalysisResult = results.get(word);

            fullResultsByWord.put(word, new WordFrequencyAnalysisResultFullItemInfo(
                    temporaryAnalysisResult.frequency, new ArrayList<>()));

            Collection<List<UUID>> itemLists = temporaryAnalysisResult.itemIdsPerFeedId.values();
            for (List<UUID> itemIds : itemLists) {
                for (UUID item: itemIds) {
                    FeedItem feedItem = notChangedFeeds.get(item);
                    fullResultsByWord.get(word).feedItems.add(feedItem);
                }
            }
        }
        return fullResultsByWord;
    }

    @AllArgsConstructor
    class TemporaryAnalysisResult {
        @Getter
        private int frequency;
        public final Map<Integer, List<UUID>> itemIdsPerFeedId;

        void increaseFrequency(){
            frequency++;
        }
    }



}
