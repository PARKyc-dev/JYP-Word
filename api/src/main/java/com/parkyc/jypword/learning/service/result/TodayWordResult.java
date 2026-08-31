package com.parkyc.jypword.learning.service.result;

import com.parkyc.jypword.wordbook.domain.WordBook;
import lombok.Data;

@Data
public class TodayWordResult {
    private WordBook wordBook;
    // words
    // private List<WordBookItem> words;
}
