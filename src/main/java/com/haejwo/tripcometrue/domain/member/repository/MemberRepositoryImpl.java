package com.haejwo.tripcometrue.domain.member.repository;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.haejwo.tripcometrue.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findByNicknameOrderByMemberRating(String nickname) {
        return queryFactory
            .selectFrom(member)
            .where(
                containsIgnoreCaseNickname(nickname)
            )
            .orderBy(member.memberRating.desc())
            .fetch();
    }

    @Override
    public Slice<Member> findByNicknameOrderByMemberRating(String nickname, Pageable pageable) {

        int pageSize = pageable.getPageSize();

        List<Member> content = queryFactory
            .selectFrom(member)
            .where(
                containsIgnoreCaseNickname(nickname)
            )
            .orderBy(member.memberRating.desc())
            .offset(pageable.getOffset())
            .limit(pageSize + 1)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageSize) {
            content.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression containsIgnoreCaseNickname(String nickname) {

        if (!StringUtils.hasText(nickname)) {
            return null;
        }

        String replacedNickname = nickname.replaceAll(" ", "");

        return Expressions.stringTemplate("function('replace',{0},{1},{2})", member.memberBase.nickname, " ", "")
            .containsIgnoreCase(replacedNickname);
    }
}
