package com.parkyc.jypword.learning.repository;

import com.parkyc.jypword.learning.domain.Learning;
import com.parkyc.jypword.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningJpaRepository extends JpaRepository<Learning, Long> {

    // 현재 학습중인 워드북 정보를 가져오는 메소드

    List<Learning> findAllByMember(Member member);
}
