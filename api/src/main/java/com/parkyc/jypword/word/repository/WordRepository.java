package com.parkyc.jypword.word.repository;

import com.parkyc.jypword.word.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}
