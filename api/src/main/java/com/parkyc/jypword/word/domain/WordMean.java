package com.parkyc.jypword.word.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Table(name = "word_mean")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class WordMean {

    @Id
    @Column(name = "mean_id")
    private Long meanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    @Enumerated(EnumType.STRING)
    @Column(name = "mean_type")
    private MeanType meanType;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
}
