package com.edytagodniak.hiring_exercisesoftware_developer.analysis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;


@Setter
@Getter
@Table(name = "ITEM")
@Entity
class ItemEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feed_id", nullable = false)
    private int feedId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link", nullable = false)
    @Lob private String link;

    @ManyToMany(mappedBy = "items")
    List<AnalysisResultEntity> analysisResults;


}
