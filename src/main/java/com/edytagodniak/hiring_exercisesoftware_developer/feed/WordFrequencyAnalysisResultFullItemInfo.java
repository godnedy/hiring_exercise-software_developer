package com.edytagodniak.hiring_exercisesoftware_developer.feed;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WordFrequencyAnalysisResultFullItemInfo {

    public final int frequency;

    public final List<FeedItem> feedItems;
}
