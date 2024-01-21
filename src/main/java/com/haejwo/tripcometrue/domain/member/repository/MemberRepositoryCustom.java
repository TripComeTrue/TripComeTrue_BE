package com.haejwo.tripcometrue.domain.member.repository;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findByNicknameOrderByMemberRating(String nickname);

    Slice<Member> findByNicknameOrderByMemberRating(String nickname, Pageable pageable);
}
