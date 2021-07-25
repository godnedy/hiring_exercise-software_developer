package com.edytagodniak.hiring_exercisesoftware_developer.feed;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
@AllArgsConstructor
public class FeedItem {
    public final UUID itemId;
    public final Integer feedId;
    public final String title;
    public final String link;
}
