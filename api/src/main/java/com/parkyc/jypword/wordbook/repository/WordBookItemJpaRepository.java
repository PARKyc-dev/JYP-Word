package com.parkyc.jypword.wordbook.repository;

import com.parkyc.jypword.wordbook.domain.WordBookItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordBookItemJpaRepository extends JpaRepository<WordBookItem, Long> {

    List<WordBookItem> findAllByWordBookWordBookIdOrderBySequenceAsc(Long wordBookId);
}
