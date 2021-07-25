package com.edytagodniak.hiring_exercisesoftware_developer.analysis;

import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedUrl;
import com.edytagodniak.hiring_exercisesoftware_developer.api.SingleWordAnalysisSummary;
import com.edytagodniak.hiring_exercisesoftware_developer.feed.FeedProcessingService;
import com.edytagodniak.hiring_exercisesoftware_developer.feed.WordFrequencyAnalysisResultFullItemInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AnalysisService {

    private final FeedProcessingService feedProcessingService;
    private final AnalysisHandler analysisHandler;
    private final AnalysisFinder analysisFinder;

    /**
     * Method analysing feeds. Feeds are analysed in memory and only results of analysis (top 3 words) are saved in the database
     * @param feedUrls list of feed ids
     *
     */
    @Transactional
    public UUID analyseFeeds(List<FeedUrl> feedUrls) {
        Map<String, WordFrequencyAnalysisResultFullItemInfo> analysisResult = feedProcessingService.analyzeFeeds(feedUrls);
        return analysisHandler.saveAnalysisResults(analysisResult);
    }

    public List<SingleWordAnalysisSummary> getAnalysisResults(UUID analysisId) {
        return analysisFinder.getAnalysisResults(analysisId);
    }

}
