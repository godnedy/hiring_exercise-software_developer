package com.edytagodniak.hiring_exercisesoftware_developer.boundry;

import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedUrl;
import com.edytagodniak.hiring_exercisesoftware_developer.feed.FeedItem;

import java.util.List;
import java.util.stream.Stream;

public interface FeedItemProvider {

    /**
     * Returns stream of feed items found in all feeds from the list
     * @param urls list of feeds
     *
     */
    Stream<FeedItem> processFeedsFromUrls(List<FeedUrl> urls);
}
