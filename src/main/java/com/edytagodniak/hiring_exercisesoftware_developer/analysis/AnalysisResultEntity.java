package com.edytagodniak.hiring_exercisesoftware_developer.analysis;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Table(name = "ANALYSIS_RESULT")
@Entity
class AnalysisResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id", nullable = false)
    private Long resultId;

    @Column(name = "analysis_id", nullable = false)
    private UUID analysisId;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "frequency", nullable = false)
    private int frequency;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "ANALYSIS_RESULT_ITEM",
            joinColumns = { @JoinColumn(name = "result_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    List<ItemEntity> items = new ArrayList<>();

}
