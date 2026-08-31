package com.parkyc.jypword.wordbook.repository;

import com.parkyc.jypword.wordbook.domain.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordBookJpaRepository extends JpaRepository<WordBook, Long> {
}
