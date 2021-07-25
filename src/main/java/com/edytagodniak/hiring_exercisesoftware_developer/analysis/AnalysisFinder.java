package com.edytagodniak.hiring_exercisesoftware_developer.analysis;

import com.edytagodniak.hiring_exercisesoftware_developer.api.AnalysisIdDoesNotExist;
import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedItemInfo;
import com.edytagodniak.hiring_exercisesoftware_developer.api.SingleWordAnalysisSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
class AnalysisFinder {

    private final AnalysisResultRepository analysisResultRepository;

    public List<SingleWordAnalysisSummary> getAnalysisResults(UUID analysisId) {
        List<AnalysisResultEntity> analysisResults = analysisResultRepository.findAllByAnalysisId(analysisId);
        if (analysisResults.isEmpty()) {
            throw new AnalysisIdDoesNotExist();
        }

        return  analysisResults.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    SingleWordAnalysisSummary map(AnalysisResultEntity entity) {
        Map<Integer, List<ItemEntity>> itemsGroupedByFeeds = entity.getItems().stream()
                .sorted(Comparator.comparingInt(ItemEntity::getFeedId))
                .collect(Collectors.groupingBy(ItemEntity::getFeedId));

        SingleWordAnalysisSummary singleWordAnalysisSummary = new SingleWordAnalysisSummary();
        singleWordAnalysisSummary.setWord(entity.getWord());
        singleWordAnalysisSummary.setFrequency(entity.getFrequency());

        Map<Integer, List<FeedItemInfo>> feedItemInfosByFeedNo = new HashMap<>();

        for (Integer feedNo : itemsGroupedByFeeds.keySet()) {
            List<FeedItemInfo> feedItemInfos = itemsGroupedByFeeds.get(feedNo).stream()
                    .map(this::map)
                    .collect(Collectors.toList());
            feedItemInfosByFeedNo.put(feedNo, feedItemInfos);
        }

        singleWordAnalysisSummary.setFeedItemInfos(feedItemInfosByFeedNo.values());

        return singleWordAnalysisSummary;
    }

    FeedItemInfo map(ItemEntity itemEntity) {
        FeedItemInfo feedItemInfo = new FeedItemInfo();
        feedItemInfo.setTitle(itemEntity.getTitle());
        feedItemInfo.setLink(itemEntity.getLink());
        return feedItemInfo;
    }
}
