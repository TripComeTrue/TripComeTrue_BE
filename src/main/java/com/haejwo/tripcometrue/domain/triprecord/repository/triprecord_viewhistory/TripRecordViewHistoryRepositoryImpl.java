package com.haejwo.tripcometrue.domain.triprecord.repository.triprecord_viewhistory;

import com.haejwo.tripcometrue.domain.triprecord.dto.query.TripRecordViewHistoryGroupByQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.haejwo.tripcometrue.domain.member.entity.QMember.member;
import static com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecord.tripRecord;
import static com.haejwo.tripcometrue.domain.triprecord.entity.QTripRecordViewHistory.tripRecordViewHistory;

@RequiredArgsConstructor
public class TripRecordViewHistoryRepositoryImpl implements TripRecordViewHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TripRecordViewHistoryGroupByQueryDto> findTopListMembers(LocalDateTime start, LocalDateTime end, int size) {
        NumberPath<Long> aliasTotalCount = Expressions.numberPath(Long.class, "totalCount");

        return queryFactory.select(
                Projections.constructor(
                    TripRecordViewHistoryGroupByQueryDto.class,
                    member.id,
                    member.memberBase.nickname,
                    member.introduction,
                    member.profileImage,
                    tripRecordViewHistory.id.count().as(aliasTotalCount)
                )
            )
            .from(tripRecordViewHistory)
            .join(tripRecordViewHistory.tripRecord, tripRecord)
            .join(tripRecord.member, member)
            .where(
                tripRecordViewHistory.updatedAt.between(start, end),
                member.memberRating.goe(3.0)
            )
            .groupBy(member.id)
            .orderBy(aliasTotalCount.desc())
            .limit(size)
            .fetch();
    }
}
