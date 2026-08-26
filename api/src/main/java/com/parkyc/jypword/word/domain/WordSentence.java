package com.parkyc.jypword.word.domain;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "word_sentence")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class WordSentence {

    @Id
    @Column(name = "sentence_id")
    private Long sentenceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
}
