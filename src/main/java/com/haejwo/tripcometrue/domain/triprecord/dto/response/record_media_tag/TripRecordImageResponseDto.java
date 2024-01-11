package com.haejwo.tripcometrue.domain.triprecord.dto.response.record_media_tag;

import com.haejwo.tripcometrue.domain.triprecord.entity.TripRecordImage;
import com.haejwo.tripcometrue.domain.triprecord.entity.type.ExternalLinkTagType;
import lombok.Builder;

public record TripRecordImageResponseDto(
    Long id,
    String imageUrl,
    ExternalLinkTagType tagType,
    String tagUrl,
    Long tripRecordId

) {

    @Builder
    public TripRecordImageResponseDto(Long id, String imageUrl, ExternalLinkTagType tagType,
        String tagUrl, Long tripRecordId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.tagType = tagType;
        this.tagUrl = tagUrl;
        this.tripRecordId = tripRecordId;
    }

    public static TripRecordImageResponseDto fromEntity(TripRecordImage entity) {
        return TripRecordImageResponseDto.builder()
            .id(entity.getId())
            .imageUrl(entity.getImageUrl())
            .tagType(entity.getTagType())
            .tagUrl(entity.getTagUrl())
            .tripRecordId(entity.getTripRecord().getId())
            .build();
    }

}
