package com.edytagodniak.hiring_exercisesoftware_developer.feed;

import com.edytagodniak.hiring_exercisesoftware_developer.api.FeedUrl;
import com.edytagodniak.hiring_exercisesoftware_developer.boundry.FeedItemProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class FeedProcessingServiceTest {

    @Mock
    FeedItemProvider feedItemProvider;

    @InjectMocks
    FeedProcessingService feedProcessingService;


    @Test
    void analyseFeeds() {
        //given
        FeedUrl feedUrl1 = new FeedUrl("https://news.egodniak.com");
        FeedUrl feedUrl2 = new FeedUrl("https://news.egodniak2.com");

        Integer feedNo1 = 1;
        UUID itemUuid1 = UUID.randomUUID();
        UUID itemUuid2 = UUID.randomUUID();
        FeedItem f1i1 = new FeedItem(itemUuid1, feedNo1, "Aaa bbb ccc", "f1i1link");
        FeedItem f1i2 = new FeedItem(itemUuid2, feedNo1, "Ddd eee fff", "f1i1link");

        Integer feedNo2 = 2;
        UUID itemUuid3 = UUID.randomUUID();
        UUID itemUuid4 = UUID.randomUUID();
        FeedItem f2i1 = new FeedItem(itemUuid3, feedNo2, "Ggg hhh aaa", "f1i1link");
        FeedItem f2i2 = new FeedItem(itemUuid4, feedNo2, "Jjj aaa lll", "f1i1link");

        Integer feedNo3 = 3;
        UUID itemUuid5 = UUID.randomUUID();
        UUID itemUuid6 = UUID.randomUUID();
        FeedItem f3i1 = new FeedItem(itemUuid5, feedNo3, "Mmm nnn cCc", "f1i1link");
        FeedItem f3i2 = new FeedItem(itemUuid6, feedNo3, "Ppp bbb aaa", "f1i1link");

        Integer feedNo4 = 4;
        UUID itemUuid7 = UUID.randomUUID();
        UUID itemUuid8 = UUID.randomUUID();
        FeedItem f4i1 = new FeedItem(itemUuid7, feedNo4, "Sss aaa bbb", "f1i1link");
        FeedItem f4i2 = new FeedItem(itemUuid8, feedNo4, "Vvv vvv vvv", "f1i1link");
        // 4x aaa, 3x bbb, 2ccc

        Stream<FeedItem> feedItemStream = Stream.of(f1i1, f1i2, f2i1, f2i2, f3i1, f3i2, f4i1, f4i2);

        List<FeedUrl> feedUrls = List.of(feedUrl1, feedUrl2);
        Mockito.when(feedItemProvider.processFeedsFromUrls(feedUrls)).thenReturn(feedItemStream);

        //when
        Map<String, WordFrequencyAnalysisResultFullItemInfo> resultMap = feedProcessingService.analyzeFeeds(feedUrls);

        //then
        System.out.println(resultMap.keySet());
        WordFrequencyAnalysisResultFullItemInfo aaa = resultMap.get("aaa");
        Assertions.assertEquals(4, aaa.frequency);
        Assertions.assertEquals(5, aaa.feedItems.size());
        Assertions.assertTrue(aaa.feedItems.containsAll(List.of(f1i1, f2i1, f2i2, f3i2, f4i1)));


        WordFrequencyAnalysisResultFullItemInfo bbb = resultMap.get("bbb");
        Assertions.assertEquals(3, bbb.frequency);
        Assertions.assertEquals(3, bbb.feedItems.size());
        Assertions.assertTrue(bbb.feedItems.containsAll(List.of(f1i1, f3i2, f4i1)));

        WordFrequencyAnalysisResultFullItemInfo ccc = resultMap.get("ccc");
        Assertions.assertEquals(2, ccc.frequency);
        Assertions.assertEquals(2, ccc.feedItems.size());
        Assertions.assertTrue(ccc.feedItems.containsAll(List.of(f1i1, f3i1)));
    }

}