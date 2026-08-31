package com.parkyc.jypword.learning.repository;

import com.parkyc.jypword.wordBook.domain.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordBookJpaRepository extends JpaRepository<WordBook, Long> {
}
