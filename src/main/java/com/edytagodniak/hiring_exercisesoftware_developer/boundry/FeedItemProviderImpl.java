package com.edytagodniak.hiring_exercisesoftware_developer.boundry;

import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedProcessingException;
import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedUrl;
import com.edytagodniak.hiring_exercisesoftware_developer.feed.FeedItem;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component
class FeedItemProviderImpl implements FeedItemProvider {

    public Stream<FeedItem> processFeedsFromUrls(List<FeedUrl> urls) {
        Stream<FeedItem> feedItemStream = Stream.<FeedItem>builder().build();

        int feedId = 1;
        for (FeedUrl feedUrl : urls) {
            Stream<FeedItem> feedItemsFromOneUrl;
            try {
                URL url = new URL(feedUrl.getUrl());
                feedItemsFromOneUrl = getFeedItemsFromOneUrl(url, feedId);
            } catch (IOException | FeedException e) {
                throw new FeedProcessingException();
            }
            feedItemStream = Stream.concat(feedItemStream, feedItemsFromOneUrl);
            feedId ++;
        }
        return feedItemStream;
    }


    private Stream<FeedItem> getFeedItemsFromOneUrl(URL url, int feedId) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));
        List<SyndEntryImpl> entries = (List<SyndEntryImpl>)feed.getEntries();

        return entries.stream()
                .map( e -> map(e, feedId));
    }

    private FeedItem map(SyndEntry entry, int feedId) {
        return new FeedItem(UUID.randomUUID(), feedId, entry.getTitle(), entry.getLink());
    }
}
