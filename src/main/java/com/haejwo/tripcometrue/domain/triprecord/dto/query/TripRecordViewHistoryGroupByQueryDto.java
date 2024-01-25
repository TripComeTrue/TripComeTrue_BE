package com.haejwo.tripcometrue.domain.triprecord.dto.query;

public record TripRecordViewHistoryGroupByQueryDto(
    Long memberId,
    String nickname,
    String introduction,
    String profileImageUrl,
    Long totalCount
) {
}
