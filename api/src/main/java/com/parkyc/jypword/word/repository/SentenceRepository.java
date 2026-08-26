package com.parkyc.jypword.word.repository;

import com.parkyc.jypword.word.domain.WordSentence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceRepository extends JpaRepository<WordSentence, Long> {
}
