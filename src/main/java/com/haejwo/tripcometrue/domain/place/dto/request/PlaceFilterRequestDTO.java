package com.haejwo.tripcometrue.domain.place.dto.request;

public record PlaceFilterRequestDTO(
    Integer stored_count,
    Integer storedCount
) {

    // record는 Compact Constructor라는 기능있어, 생성자 내부의 변수에 대한 로직이 마지막으로 동작하여 변수 초기화를 한다.
    public PlaceFilterRequestDTO {
        storedCount = stored_count;
    }

}
