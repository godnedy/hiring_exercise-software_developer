package com.edytagodniak.hiring_exercisesoftware_developer.api;

import com.edytagodniak.hiring_exercisesoftware_developer.analysis.AnalysisService;
import com.edytagodniak.hiring_exercisesoftware_developer.validation.MinSize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Validated
@RestController
@RequiredArgsConstructor
public class FeedController {

    private final AnalysisService analysisService;

    /**
     * Method is analysing provided feeds
     * @return id of analysis to be used in the second endpoint
     *
     */
    @PostMapping("/analyse/new")
    public ResponseEntity<String> analyse(@RequestBody @MinSize List<@Valid FeedUrl> feedURIs) {
        UUID analysisId = analysisService.analyseFeeds(feedURIs);
        return ResponseEntity.ok(analysisId.toString());
    }

    /**
     * Method returns summary of single analysis. Contains summary for three most frequent words.
     * @param analysisUuid id of feed analysis
     *
     */
    @GetMapping("/frequency/{id}")
    public ResponseEntity<List<SingleWordAnalysisSummary>> getServicesVersions(@PathVariable(name = "id") UUID analysisUuid) {
        return ResponseEntity.ok(analysisService.getAnalysisResults(analysisUuid));
    }


    @ExceptionHandler(AnalysisIdDoesNotExist.class)
    public ResponseEntity<Object> handleAnalysisIdDoesNotExistException(AnalysisIdDoesNotExist e) {
        return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(FeedProcessingException.class)
    public ResponseEntity<Object> handleFeedProcessingException(FeedProcessingException e) {
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" " + violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occured.";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
