package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import lombok.Builder;

import java.util.Objects;
import java.util.Set;

public record TripRecordListItemResponseDto(
    Long tripRecordId,
    String tripRecordTitle,
    Long memberId,
    String memberName,
    String profileImageUrl,
    Set<String> cityNames,
    Integer totalDays,
    Integer storedCount,
    String imageUrl
) {

    @Builder
    public TripRecordListItemResponseDto {
    }

    public static TripRecordListItemResponseDto fromEntity(
        TripRecord entity, Set<String> cityNames, Member member
    ) {
        boolean isPresentMember = Objects.nonNull(member);
        return TripRecordListItemResponseDto.builder()
            .tripRecordId(entity.getId())
            .tripRecordTitle(entity.getTitle())
            .memberId(
                isPresentMember ? member.getId() : null
            )
            .memberName(
                isPresentMember ? member.getMemberBase().getNickname() : null
            )
            .profileImageUrl(
                isPresentMember ? member.getProfileImage() : null
            )
            .cityNames(cityNames)
            .totalDays(entity.getTotalDays())
            .storedCount(entity.getStoreCount())
            .imageUrl(
                entity.getTripRecordImages()
                    .stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(TripRecordImage::getImageUrl)
                    .orElse(null)
            )
            .build();
    }
}
