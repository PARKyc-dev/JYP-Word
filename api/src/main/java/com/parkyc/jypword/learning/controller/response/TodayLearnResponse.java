package com.parkyc.jypword.learning.controller.response;

import com.parkyc.jypword.word.service.dto.WordResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TodayLearnResponse {
    /** 금일 학습할 단어 목록 리턴 */

    private LocalDateTime learningAt; // YYYY-MM-DD

    private Long wordBookId;

    private int startCursor;

    private int endCursor;

    private List<WordResponse> words;
}
