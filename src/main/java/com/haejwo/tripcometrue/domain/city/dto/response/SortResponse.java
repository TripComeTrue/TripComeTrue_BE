package com.haejwo.tripcometrue.domain.city.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public record SortResponse(
    Boolean sorted,
    String direction,
    String orderProperty
) {

    @Builder
    public SortResponse {
    }

    public static SortResponse of(Sort sort) {
        Sort.Order order = sort.get().findFirst().orElse(null);

        return SortResponse.builder()
            .sorted(sort.isSorted())
            .direction(Objects.nonNull(order) ? order.getDirection().name() : null)
            .orderProperty(Objects.nonNull(order) ? order.getProperty() : null)
            .build();
    }
}
