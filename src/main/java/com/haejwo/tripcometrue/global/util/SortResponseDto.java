package com.haejwo.tripcometrue.global.util;

import lombok.Builder;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public record SortResponseDto(
    Boolean sorted,
    String direction,
    String orderProperty
) {

    @Builder
    public SortResponseDto {
    }

    public static SortResponseDto of(Sort sort) {
        Sort.Order order = sort.get().findFirst().orElse(null);

        return SortResponseDto.builder()
            .sorted(sort.isSorted())
            .direction(Objects.nonNull(order) ? order.getDirection().name() : null)
            .orderProperty(Objects.nonNull(order) ? order.getProperty() : null)
            .build();
    }
}
