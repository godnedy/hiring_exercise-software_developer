package com.edytagodniak.hiring_exercisesoftware_developer.api;

public class AnalysisIdDoesNotExist extends RuntimeException {
    public AnalysisIdDoesNotExist() {
        super("Provided analysis id does not exists!");
    }
}
