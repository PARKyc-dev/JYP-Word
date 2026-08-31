package com.parkyc.jypword.learning.controller.request;

import com.parkyc.jypword.member.domain.Member;
import lombok.Data;


@Data
public class TodayLearnRequest {

    private Member member; // 회원 ID(UUID)를 넣으면 금일 공부할 내용은 리턴한다. 오늘 공부할 내용은 브라우저에 저장하고 사용한다.
}
