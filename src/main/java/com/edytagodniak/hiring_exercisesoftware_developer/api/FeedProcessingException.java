package com.edytagodniak.hiring_exercisesoftware_developer.api;

public class FeedProcessingException extends RuntimeException {
    public FeedProcessingException() {
        super("There was an exception during feed processing");
    }
}
