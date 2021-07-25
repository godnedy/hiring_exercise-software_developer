package com.edytagodniak.hiring_exercisesoftware_developer.api;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

/**
 * Summary of single words analysis.
 * Contains word, frequency and lists of feed items (separate list per each feed (url))
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleWordAnalysisSummary {
    String word;
    Integer frequency;
    Collection<List<FeedItemInfo>> feedItemInfos;
}
