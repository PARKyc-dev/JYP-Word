package com.parkyc.jypword.learning.domain;

import com.parkyc.jypword.wordBook.domain.WordBook;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Table(name = "learning")
@Entity
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(
        name = "SEQ_LEARNING",
        sequenceName = "SEQ_LEARNING",
        allocationSize = 1,
        initialValue = 1
)
public class Learning {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEARNING")
    private Long learningId;

    @Column(name = "member_id")
    private String memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_book_id")
    private WordBook wordBook;

    @Column(name = "start_cursor")
    private int startCursor;

    @Column(name = "current_cursor")
    private int currentCursor;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "completedAt")
    private Instant completedAt;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;
}
