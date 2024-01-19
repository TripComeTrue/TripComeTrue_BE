package com.haejwo.tripcometrue.global.util;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponseDto<T>(
    List<T> content,
    SortResponseDto sort,
    Integer totalCount,
    Integer currentPageNum,
    Integer pageSize,
    Boolean first,
    Boolean last
) {

    @Builder
    public SliceResponseDto {
    }

    public static <T> SliceResponseDto<T> of(Slice<T> slice) {
        return SliceResponseDto.<T>builder()
            .content(slice.getContent())
            .sort(SortResponseDto.of(slice.getSort()))
            .totalCount(slice.getNumberOfElements())
            .currentPageNum(slice.getNumber())
            .pageSize(slice.getSize())
            .first(slice.isFirst())
            .last(slice.isLast())
            .build();
    }
}
