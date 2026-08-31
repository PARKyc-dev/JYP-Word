package com.parkyc.jypword.learning.service;

import com.parkyc.jypword.learning.domain.Learning;
import com.parkyc.jypword.learning.repository.LearningJpaRepository;
import com.parkyc.jypword.learning.service.result.TodayWordResult;
import com.parkyc.jypword.member.domain.Member;
import com.parkyc.jypword.wordbook.domain.WordBook;
import com.parkyc.jypword.wordbook.repository.WordBookJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningService {

    private final LearningJpaRepository learningRepository;
    private final WordBookJpaRepository wordBookRepository;

    public TodayWordResult getTodayWordsByMember(Member member){

        // 학습중인 wordbook, cursor 확인
        List<Learning> learning = learningRepository.findAllByMember(member);

        // wordbook에서 데이터 가져옴

        // result로 변환

        return new TodayWordResult();
    }

}
