package com.edytagodniak.hiring_exercisesoftware_developer.analysis;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnalysisResultRepository extends CrudRepository<AnalysisResultEntity, UUID> {

    @Query("select distinct analysisResult from AnalysisResultEntity analysisResult " +
            " join fetch analysisResult.items item " +
            " where analysisResult.analysisId = :analysisId ")
    List<AnalysisResultEntity> findAllByAnalysisId(UUID analysisId);


}
