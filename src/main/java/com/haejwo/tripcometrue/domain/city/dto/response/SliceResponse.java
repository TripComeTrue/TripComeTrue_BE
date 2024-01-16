package com.haejwo.tripcometrue.domain.city.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

public record SliceResponse<T>(
    List<T> content,
    SortResponse sort,
    Integer totalCount,
    Integer currentPageNum,
    Integer pageSize,
    Boolean first,
    Boolean last
) {

    @Builder
    public SliceResponse {
    }

    public static <T> SliceResponse<T> of(Slice<T> slice) {
        return SliceResponse.<T>builder()
            .content(slice.getContent())
            .sort(SortResponse.of(slice.getSort()))
            .totalCount(slice.getNumberOfElements())
            .currentPageNum(slice.getNumber())
            .pageSize(slice.getSize())
            .first(slice.isFirst())
            .last(slice.isLast())
            .build();
    }
}
