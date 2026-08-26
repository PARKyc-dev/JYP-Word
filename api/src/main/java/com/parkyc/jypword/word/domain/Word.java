package com.parkyc.jypword.word.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Table(name = "word")
@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(
        name = "",
        sequenceName = "",
        allocationSize = 1,
        initialValue = 1
)
public class Word {

    @Id
    @Column(name = "word_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
    private Long wordId;

    @Column(name = "word")
    private String word;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "word")
    private List<WordMean> wordMeans;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "word")
    private List<WordSentence> wordSentences;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private WordStatus status;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
}


