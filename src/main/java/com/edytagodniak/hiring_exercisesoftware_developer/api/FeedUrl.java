package com.edytagodniak.hiring_exercisesoftware_developer.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedUrl {
    @URL
    String url;
}
