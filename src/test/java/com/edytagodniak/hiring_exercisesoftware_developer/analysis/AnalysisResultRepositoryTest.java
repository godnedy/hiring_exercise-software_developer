package com.edytagodniak.hiring_exercisesoftware_developer.analysis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

@DataJpaTest
class AnalysisResultRepositoryTest {

    @Autowired
    AnalysisResultRepository analysisResultRepository;

    @Test
    public void findAllByAnalysisId(){
        ItemEntity te1 = createTitleEntity("T1", 10);
        ItemEntity te2 = createTitleEntity("T2", 11);
        ItemEntity te3 = createTitleEntity("T3", 13);

        ItemEntity te11 = createTitleEntity("T11", 10);
        ItemEntity te21 = createTitleEntity("T21", 11);
        ItemEntity te31 = createTitleEntity("T31", 13);

        UUID analysisId1 = UUID.randomUUID();
        AnalysisResultEntity analysisResultEntity1 = new AnalysisResultEntity();
        analysisResultEntity1.setAnalysisId(analysisId1);
        analysisResultEntity1.setFrequency(3);
        analysisResultEntity1.setWord("word1");
        analysisResultEntity1.setItems(List.of(te1, te2, te3));

        AnalysisResultEntity analysisResultEntity2 = new AnalysisResultEntity();
        analysisResultEntity2.setAnalysisId(analysisId1);
        analysisResultEntity2.setFrequency(3);
        analysisResultEntity2.setWord("word2");
        analysisResultEntity2.setItems(List.of(te11, te21, te31));

        //when
        analysisResultRepository.saveAll(List.of(analysisResultEntity1, analysisResultEntity2));
        List<AnalysisResultEntity> allByAnalysisId = analysisResultRepository.findAllByAnalysisId(analysisId1);

        //then
        Assertions.assertEquals(2, allByAnalysisId.size());
        Assertions.assertEquals(3, allByAnalysisId.get(0).getItems().size());
        Assertions.assertEquals("T1", allByAnalysisId.get(0).getItems().get(0).getTitle());
        Assertions.assertEquals("T2", allByAnalysisId.get(0).getItems().get(1).getTitle());
        Assertions.assertEquals("T3", allByAnalysisId.get(0).getItems().get(2).getTitle());

        Assertions.assertEquals(3, allByAnalysisId.get(1).getItems().size());
        Assertions.assertEquals("T11", allByAnalysisId.get(1).getItems().get(0).getTitle());
        Assertions.assertEquals("T21", allByAnalysisId.get(1).getItems().get(1).getTitle());
        Assertions.assertEquals("T31", allByAnalysisId.get(1).getItems().get(2).getTitle());
    }


    private ItemEntity createTitleEntity(String title, Integer feedNo) {
        ItemEntity entity = new ItemEntity();
        entity.setTitle(title);
        entity.setLink("link");
        entity.setFeedId(feedNo);
        return entity;
    }
}