package com.haejwo.tripcometrue.domain.triprecord.dto.response.triprecord;

import com.haejwo.tripcometrue.domain.member.entity.Member;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecord;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordSchedule;
import lombok.Builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public record TopTripRecordResponseDto(
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
    public TopTripRecordResponseDto {
    }

    public static TopTripRecordResponseDto fromEntity(TripRecord entity) {
        Set<String> cityNames = new HashSet<>();
        for (TripRecordSchedule tripRecordSchedule : entity.getTripRecordSchedules()) {
            cityNames.add(tripRecordSchedule.getPlace().getCity().getName());
        }

        Member member = entity.getMember();

        return TopTripRecordResponseDto.builder()
            .tripRecordId(entity.getId())
            .tripRecordTitle(entity.getTitle())
            .memberId(member.getId())
            .memberName(member.getMemberBase().getNickname())
            .profileImageUrl(member.getProfile_image())
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
