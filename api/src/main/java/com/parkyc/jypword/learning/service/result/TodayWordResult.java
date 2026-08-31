package com.parkyc.jypword.learning.service.result;

import com.parkyc.jypword.wordBook.domain.WordBook;
import com.parkyc.jypword.wordBook.domain.WordBookItem;
import lombok.Data;

import java.util.List;

@Data
public class TodayWordResult {
    private WordBook wordBook;
    private List<WordBookItem> words;


}
