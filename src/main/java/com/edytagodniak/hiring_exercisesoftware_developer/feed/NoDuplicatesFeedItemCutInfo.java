package com.edytagodniak.hiring_exercisesoftware_developer.feed;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
class NoDuplicatesFeedItemCutInfo {
    public final UUID itemId;
    public final int feedId;
    public final List<String> noDuplicatesTitleWords;


    static NoDuplicatesFeedItemCutInfo of(FeedItem feedItem) {
        List<String> noDuplicatesTitleWords = getNoDuplicatesTitle(feedItem.title);
        return new NoDuplicatesFeedItemCutInfo(feedItem.itemId, feedItem.feedId, noDuplicatesTitleWords);
    }

    private static List<String> getNoDuplicatesTitle(String originalTitle) {
        return Arrays.stream(originalTitle.toLowerCase().split(" "))
                .distinct()
                .collect(Collectors.toList());
    }

}