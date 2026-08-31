package com.parkyc.jypword.learning.controller;

import com.parkyc.jypword.learning.controller.request.TodayLearnRequest;
import com.parkyc.jypword.learning.controller.response.TodayLearnResponse;
import com.parkyc.jypword.learning.service.LearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learn")
@RequiredArgsConstructor
public class LearningController {
    /** 사용자 주로 사용하는 API */

    private final LearningService learningService;

    // 1. 금일 학습목록 가져오기.
    @GetMapping("/today")
    public TodayLearnResponse getTodayLearnWords(TodayLearnRequest request){




        return new TodayLearnResponse();
    }

    // 2. 학습 내용 저장하기.
}
