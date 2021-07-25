package com.edytagodniak.hiring_exercisesoftware_developer.analysis;

import com.edytagodniak.hiring_exercisesoftware_developer.feed.FeedItem;
import com.edytagodniak.hiring_exercisesoftware_developer.feed.WordFrequencyAnalysisResultFullItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
class AnalysisHandler {

    private final AnalysisResultRepository analysisResultRepository;

    @Transactional
    public UUID saveAnalysisResults(Map<String, WordFrequencyAnalysisResultFullItemInfo> finalFrequencyAnalysis) {
        UUID analysisUUID = UUID.randomUUID();

        List<AnalysisResultEntity> analysisResultEntities = finalFrequencyAnalysis.keySet().stream()
                .map(word -> this.map(word, finalFrequencyAnalysis.get(word)))
                .peek(entity -> entity.setAnalysisId(analysisUUID))
                .collect(Collectors.toList());
        analysisResultRepository.saveAll(analysisResultEntities);

        return analysisUUID;
    }

   AnalysisResultEntity map(String word, WordFrequencyAnalysisResultFullItemInfo analysisResultForWord) {
        List<ItemEntity> items = analysisResultForWord.feedItems.stream()
                .map(this::map)
                .collect(Collectors.toList());

        AnalysisResultEntity newAnalysisResultEntity = new AnalysisResultEntity();
        newAnalysisResultEntity.setFrequency(analysisResultForWord.frequency);
        newAnalysisResultEntity.setWord(word);
        newAnalysisResultEntity.setItems(items);

        return newAnalysisResultEntity;
    }

    ItemEntity map(FeedItem feedItem) {
        ItemEntity entity = new ItemEntity();
        entity.setFeedId(feedItem.feedId);
        entity.setLink(feedItem.link);
        entity.setTitle(feedItem.title);
        return entity;
    }
}
